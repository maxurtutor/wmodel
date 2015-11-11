package org.maxur.wmodel;


import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.h2.tools.RunScript;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.GroupRepositoryImpl;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.dao.UserFactoryImpl;
import org.maxur.wmodel.dao.UserRepositoryImpl;
import org.maxur.wmodel.service.GroupRepository;
import org.maxur.wmodel.dao.UnitOfWorkFactory;
import org.maxur.wmodel.domain.UserFactory;
import org.maxur.wmodel.service.UserRepository;
import org.maxur.wmodel.view.MyApplicationEventListener;
import org.maxur.wmodel.view.MyRequestEventListener;
import org.maxur.wmodel.view.RuntimeExceptionHandler;
import org.maxur.wmodel.view.ValidationExceptionHandler;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Launcher extends Application<Launcher.AppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new Launcher().run("server", "./src/main/resources/wmodel.yml");
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(final AppConfiguration cfg, final Environment env) throws IOException, SQLException {
        DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        JmxReporter.forRegistry(env.metrics()).build().start();

        try (Handle h = dbi.open()) {
            try (InputStream is = getClass().getResourceAsStream("/db.ddl");
                 Reader reader = new InputStreamReader(is)
            ) {
                RunScript.execute(h.getConnection(), reader);
            }

            final String id1 = "group1";
            final String id2 = "group2";
            h.insert("INSERT INTO t_group (group_id, name) VALUES (?, ?)", id1, "developers");
            h.insert("INSERT INTO t_group (group_id, name) VALUES (?, ?)", id2, "managers");


            h.insert("INSERT INTO t_user (user_id, name, group_id) VALUES (?, ?, ?)", "user1", "Ivanov", id1);
            h.insert("INSERT INTO t_user (user_id, name, group_id) VALUES (?, ?, ?)", "user2", "Petrov", id1);
            h.insert("INSERT INTO t_user (user_id, name, group_id) VALUES (?, ?, ?)", "user3", "Sidorov", id2);
        }

        final UnitOfWorkFactory unitOfWorkFactory = new UnitOfWorkFactory(dbi);

        MyRequestEventListener.setUnitOfWorkFactory(unitOfWorkFactory);

        env.jersey().register(RuntimeExceptionHandler.class);
        env.jersey().register(ValidationExceptionHandler.class);
        env.jersey().register(MyApplicationEventListener.class);
        env.jersey().packages(getClass().getPackage().getName());
        env.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(env.lifecycle()).to(LifecycleEnvironment.class);
                bind(dbi.onDemand(UserDAO.class)).to(UserDAO.class);
                bind(dbi.onDemand(GroupDAO.class)).to(GroupDAO.class);
                bind(GroupRepositoryImpl.class).to(GroupRepository.class);
                bind(UserRepositoryImpl.class).to(UserRepository.class);
                bind(UserFactoryImpl.class).to(UserFactory.class);
                bind(unitOfWorkFactory).to(UnitOfWorkFactory.class);
            }
        });
    }

    public static class AppConfiguration extends Configuration {
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();
        public DataSourceFactory getDataSourceFactory() {
            return database;
        }
    }
}
