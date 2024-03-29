package org.ichat.backend.config;

import lombok.RequiredArgsConstructor;
import org.ichat.backend.model.Roles;
import org.ichat.backend.repository.RoleRepository;
import org.ichat.backend.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitOnStartup {
    private final RoleRepository roleRepository;

    @Bean
    public void initDatabase() {
        List<Roles> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            roleRepository.saveAll( List.of(new Roles(1, "USER", "READ")
                    ,new Roles(2, "ADMIN", "READ,WRITE,DELETE"),
                    new Roles(3, "MANAGER", "READ,WRITE")));
        }
    }

}
