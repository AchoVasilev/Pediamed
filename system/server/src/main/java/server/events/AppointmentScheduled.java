package server.events;

import io.micronaut.context.event.ApplicationEvent;

public class AppointmentScheduled extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AppointmentScheduled(Object source) {
        super(source);
    }
}
