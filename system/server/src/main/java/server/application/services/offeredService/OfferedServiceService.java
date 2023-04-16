package server.application.services.offeredService;

import jakarta.inject.Singleton;
import server.domain.repositories.OfferedServiceRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class OfferedServiceService {
    private final OfferedServiceRepository offeredServiceRepository;

    public OfferedServiceService(OfferedServiceRepository offeredServiceRepository) {
        this.offeredServiceRepository = offeredServiceRepository;
    }

    public Set<OfferedServiceView> getAll() {
        return this.offeredServiceRepository.findByDeletedFalse()
                .stream()
                .map(s -> new OfferedServiceView(s.getId(), s.getName(), s.getPrice()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
