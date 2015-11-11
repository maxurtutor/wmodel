package org.maxur.wmodel.view;

import org.maxur.wmodel.dao.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.maxur.wmodel.view.Incident.incidents;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.11.2015</pre>
 */
public class StopUoWFilter implements ContainerResponseFilter {

    private final UnitOfWork unitOfWork;

    @Inject
    public StopUoWFilter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        if (responseContext.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            unitOfWork.clear();
            return;
        }
        switch (requestContext.getMethod()) {
            case "PUT":
            case "POST":
            case "DELETE":
                commit(responseContext);
                break;
            default:
        }
    }

    private void commit(ContainerResponseContext responseContext) {
        try {
            unitOfWork.commit();
        } catch (RuntimeException e) {
            responseContext.setStatus(INTERNAL_SERVER_ERROR.getStatusCode());
            responseContext.setEntity(makeErrorEntity(e));
        }
    }

    private GenericEntity<List<Incident>> makeErrorEntity(final RuntimeException exception) {
        return new GenericEntity<List<Incident>>(incidents("System error", exception.getMessage())) {
        };
    }
}
