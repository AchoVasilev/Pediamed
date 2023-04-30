package server.application.services.sse;

import io.micronaut.http.sse.Event;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import server.application.services.schedule.models.CabinetSchedule;

@Singleton
public class ServerSentEventService {

    public Publisher<?> send(CabinetSchedule eventData) {
        return Mono.create(emitter -> {
            emitter.success(Event.of(eventData));
        });
    }
}
