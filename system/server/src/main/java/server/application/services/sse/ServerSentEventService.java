package server.application.services.sse;

import io.micronaut.http.sse.Event;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import server.application.services.schedule.models.CabinetSchedule;

@Singleton
@Slf4j
public class ServerSentEventService {

    public Publisher<?> send(CabinetSchedule eventData) {
        return Mono.create(emitter -> {
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                log.info(ex.getMessage(), ex);
            }

            emitter.success(Event.of(eventData));
        });
    }
}
