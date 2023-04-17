package server.application.services.role;

import jakarta.inject.Singleton;
import server.domain.entities.Role;
import server.domain.entities.enums.RoleEnum;
import server.domain.repositories.RoleRepository;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import static server.common.ErrorMessages.ENTITY_NOT_FOUND;

@Singleton
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(RoleEnum name) {
        return this.roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
    }
}
