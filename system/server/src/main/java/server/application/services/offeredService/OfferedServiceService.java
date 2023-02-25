package server.application.services.offeredService;

import org.springframework.stereotype.Service;
import server.infrastructure.repositories.OfferedServiceRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferedServiceService {
    private final OfferedServiceRepository offeredServiceRepository;

    public OfferedServiceService(OfferedServiceRepository offeredServiceRepository) {
        this.offeredServiceRepository = offeredServiceRepository;
    }

    public Set<OfferedServiceView> getAll() {
        return this.offeredServiceRepository.findAll()
                .stream()
                .map(s -> new OfferedServiceView(s.getId(), s.getName(), s.getPrice()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
