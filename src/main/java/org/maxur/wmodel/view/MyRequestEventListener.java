package org.maxur.wmodel.view;

import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.maxur.wmodel.domain.UnitOfWorkFactory;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>10.11.2015</pre>
 */
public class MyRequestEventListener implements RequestEventListener {

    private static UnitOfWorkFactory unitOfWorkFactory;
    private final int requestNumber;
    private final long startTime;

    public MyRequestEventListener(int requestNumber) {
        this.requestNumber = requestNumber;
        startTime = System.currentTimeMillis();
    }

    public static UnitOfWorkFactory getUnitOfWorkFactory() {
        return unitOfWorkFactory;
    }

    public static void setUnitOfWorkFactory(UnitOfWorkFactory unitOfWorkFactory) {
        MyRequestEventListener.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public void onEvent(RequestEvent event) {
        switch (event.getType()) {
            case RESOURCE_METHOD_START:
                System.out.printf("Resource method %s started for request %d%n",
                        getHttpMethod(event),
                        requestNumber
                );
                break;
            case FINISHED:
                System.out.printf("Request %d finished. Processing time %d ms.%n",
                        requestNumber,
                        System.currentTimeMillis() - startTime
                );
                if (!"GET".equalsIgnoreCase(getHttpMethod(event))) {
                    unitOfWorkFactory.provide().commit();
                }
                break;
        }
    }

    private String getHttpMethod(RequestEvent event) {
        return event.getUriInfo().getMatchedResourceMethod().getHttpMethod();
    }
}
