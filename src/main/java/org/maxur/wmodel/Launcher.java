package org.maxur.wmodel;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Launcher extends Application<Configuration> {

    public static void main(final String[] args) throws Exception {
        new Launcher().run("server");
    }

    @Override
    public void run(final Configuration configuration, final Environment environment) {
        environment.jersey().register(new HelloWorldResource());
    }

    @Path("/hello-world")
    @Produces(MediaType.APPLICATION_JSON)
    public static class HelloWorldResource {
        @GET
        public Map<String, Object> sayHello(@QueryParam("name") String name) {
            Map<String, Object> res = new HashMap<>();
            res.put("content", "Hello, " + (name != null ? name : "World"));
            return res;
        }
    }

}