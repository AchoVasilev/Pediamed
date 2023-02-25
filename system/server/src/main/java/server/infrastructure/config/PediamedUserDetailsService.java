package server.infrastructure.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.infrastructure.repositories.UserRepository;

import java.util.Set;

import static server.application.constants.ErrorMessages.INVALID_CREDENTIALS;

@Service
public class PediamedUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public PediamedUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(INVALID_CREDENTIALS));

        var authorities = Set.of(new SimpleGrantedAuthority(user.getRole().getName().name()));
        return new User(user.getEmail().getEmail(), user.getPassword(), authorities);
    }
}
