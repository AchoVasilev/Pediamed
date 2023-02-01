package server.features.cabinet;

import org.springframework.stereotype.Service;
import server.DAL.repositories.CabinetRepository;
import server.features.cabinet.models.CabinetResponse;

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
                        c.getCity()
                )).collect(Collectors.toList());
    }
}
