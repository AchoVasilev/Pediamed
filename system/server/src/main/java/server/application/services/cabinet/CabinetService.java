package server.application.services.cabinet;

import jakarta.inject.Singleton;
import server.domain.entities.Cabinet;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.CabinetRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static server.common.ErrorMessages.CABINET_NOT_FOUND;

@Singleton
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    @Transactional
    public List<CabinetResponse> getCabinets() {
        return this.cabinetRepository
                .findByDeletedFalse()
                .stream()
                .map(c -> new CabinetResponse(
                        c.getId(),
                        c.getName(),
                        c.getCity(),
                        new CabinetScheduleResponse(
                                c.getSchedule().getId()
                        )
                )).collect(Collectors.toList());
    }

    public Cabinet getCabinetByCity(String name) {
        return this.cabinetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
    }

    public void saveCabinet(Cabinet cabinet) {
        this.cabinetRepository.save(cabinet);
    }

    public CabinetResponse getCabinetByName(String name) {
        return this.cabinetRepository.findByName(name)
                .map(c -> new CabinetResponse(
                        c.getId(),
                        c.getName(),
                        c.getCity(),
                        new CabinetScheduleResponse(
                                c.getSchedule().getId()
                        )
                )).orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
    }
}
