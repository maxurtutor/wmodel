package org.maxur.wmodel.view;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>10.11.2015</pre>
 */
public class MyApplicationEventListener implements ApplicationEventListener {

    private volatile int requestCnt = 0;

    @Override
    public void onEvent(ApplicationEvent event) {
        switch (event.getType()) {
            case INITIALIZATION_FINISHED:
                System.out.printf("Application %s was initialized.%n", event.getResourceConfig().getApplicationName());
                break;
            case DESTROY_FINISHED:
                System.out.printf("Application %sdestroyed.%n", event.getResourceConfig().getApplicationName());
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        requestCnt++;
        System.out.println("Request " + requestCnt + " started.");
        return new MyRequestEventListener(requestCnt);
    }
}