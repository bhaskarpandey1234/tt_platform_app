package com.project.irctc.repository;

import com.project.irctc.model.appuserrole.AppUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(@Email(message = "Email should be valid")
                          @NotBlank(message = "Email is mandatory") String email);
}
