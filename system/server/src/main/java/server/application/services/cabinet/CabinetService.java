//package server.application.services.cabinet;
//
//import org.springframework.stereotype.Service;
//import server.domain.entities.Cabinet;
//import server.infrastructure.repositories.CabinetRepository;
//import server.infrastructure.config.exceptions.models.EntityNotFoundException;
//import server.application.services.schedule.models.CabinetSchedule;
//import server.application.services.schedule.models.ScheduleAppointment;
//import server.application.services.schedule.models.ScheduleEvent;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static server.application.constants.ErrorMessages.CABINET_NOT_FOUND;
//
//@Service
//public class CabinetService {
//    private final CabinetRepository cabinetRepository;
//
//    public CabinetService(CabinetRepository cabinetRepository) {
//        this.cabinetRepository = cabinetRepository;
//    }
//
//    public List<CabinetResponse> getCabinets() {
//        return this.cabinetRepository
//                .findAll()
//                .stream()
//                .map(c -> new CabinetResponse(
//                        c.getId(),
//                        c.getName(),
//                        c.getCity(),
//                        new CabinetSchedule(
//                                c.getSchedule().getId(),
//                                c.getSchedule().getAppointments()
//                                        .stream()
//                                        .map(a -> new ScheduleAppointment(a.getId(), a.getStartDate(), a.getEndDate(), a.getTitle()))
//                                        .collect(Collectors.toList()),
//                                c.getSchedule().getCalendarEvents()
//                                        .stream()
//                                        .map(e -> new ScheduleEvent(e.getId(), e.getStartDate(), e.getEndDate(), e.getTitle()))
//                                        .collect(Collectors.toList())
//                        )
//                )).collect(Collectors.toList());
//    }
//
//    public Cabinet getCabinetByCity(String name) {
//        return this.cabinetRepository.findByName(name)
//                .orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
//    }
//
//    public void saveCabinet(Cabinet cabinet) {
//        this.cabinetRepository.save(cabinet);
//    }
//}
