package server.features.schedule;

import org.springframework.stereotype.Service;
import server.DAL.repositories.EventDataRepository;
import server.features.schedule.models.EventDataResponse;

import java.util.List;

@Service
public class ScheduleService {
    private  final EventDataRepository eventDataRepository;

    public ScheduleService(EventDataRepository eventDataRepository) {
        this.eventDataRepository = eventDataRepository;
    }

    public List<EventDataResponse> getEventData() {
        return this.eventDataRepository
                .findAll()
                .stream()
                .map(ev -> new EventDataResponse(ev.getHours(), ev.getIntervals()))
                .toList();
    }
}
