package server.features.schedule;

import org.springframework.stereotype.Service;
import server.DAL.repositories.CabinetRepository;
import server.features.schedule.models.CabinetResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    public List<CabinetResponse> getCabinets() {
        return this.cabinetRepository
                .findAll()
                .stream()
                .map(c -> new CabinetResponse(
                        c.getId(),
                        c.getName(),
                        c.getCity(),
                        c.getAddress(),
                        c.getPostCode(),
                        c.getPhoneNumber()
                )).collect(Collectors.toList());
    }
}
