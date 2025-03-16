package com.project.irctc.service;


import com.project.irctc.exception.ResourceNotFoundException;
import com.project.irctc.model.appuserrole.AppUser;
import com.project.irctc.repository.AppUserRepository;
import com.project.irctc.repository.AppUserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;
    private final AppUserRoleRepository userRoleRepository;

    public MyUserDetailsService(AppUserRepository userRepository, AppUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Fetch the user by username
            AppUser appUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

            // Fetch roles associated with the user from the UserRole table
            List<String> roles = userRoleRepository.findByUserId(appUser.getId()).stream()
                    .map(userRole -> userRole.getRole().getName().name()) // Convert RoleEnum to String
                    .collect(Collectors.toList());

            // Map roles to SimpleGrantedAuthority
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            // Return the UserDetails object
            return new User(appUser.getUsername(), appUser.getPassword(), authorities);

        } catch (ResourceNotFoundException ex) {
            log.error("Error loading user by username: {}", username, ex);
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Unexpected error while loading user by username: {}", username, ex);
            throw new UsernameNotFoundException("An unexpected error occurred while loading user details.", ex);
        }
    }
}
