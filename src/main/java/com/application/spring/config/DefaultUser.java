package com.application.spring.config;

import com.application.spring.model.entity.RoleEnum;
import com.application.spring.model.entity.Users;
import com.application.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void defaultUser() {
        Users user = new Users();
        user.setId(UUID.randomUUID());
        user.setEmail("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setFirstName("Admin");
        user.setLastName("Adminovich");
        user.setRoles(List.of(RoleEnum.ROLE_ADMIN));
        userRepository.save(user);
    }
}
