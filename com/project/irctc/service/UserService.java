package com.project.irctc.service;

import com.project.irctc.dto.request.LoginRequestDTO;
import com.project.irctc.dto.response.ResponseDTO;
import com.project.irctc.enums.RoleName;
import com.project.irctc.dto.AppUserDTO;
import com.project.irctc.dto.response.AuthResponseDTO;
import com.project.irctc.exception.AuthenticationFailedException;
import com.project.irctc.exception.ResourceNotFoundException;
import com.project.irctc.model.appuserrole.AppUser;
import com.project.irctc.model.appuserrole.AppUserRole;
import com.project.irctc.model.token.JwtToken;
import com.project.irctc.model.appuserrole.MasterRole;
import com.project.irctc.repository.AppUserRepository;
import com.project.irctc.repository.AppUserRoleRepository;
import com.project.irctc.repository.JwtTokenRepository;
import com.project.irctc.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;
    private final JwtTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final AppUserRoleRepository userRoleRepository;

    @Transactional
    public ResponseEntity<AuthResponseDTO> register(AppUserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO("Username already exists"));
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO("Email already exists"));
        }

        MasterRole userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        AppUser user = new AppUser();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);

        AppUser savedUser = userRepository.save(user);

        AppUserRole userRoleAssociation = new AppUserRole();
        userRoleAssociation.setUser(savedUser);
        userRoleAssociation.setRole(userRole);
        userRoleRepository.save(userRoleAssociation);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        String token = jwtService.generateToken(userDetails);

        JwtToken jwtToken = new JwtToken(null, user.getId(), token, System.currentTimeMillis() + 3600000);
        tokenRepository.save(jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(token));
    }

    @Transactional
    public AuthResponseDTO login( LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtService.generateToken(userDetails);

                AppUser appUser = userRepository.findByUsername(loginRequestDTO.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                JwtToken jwtToken = new JwtToken(null, appUser.getId(), token, System.currentTimeMillis() + 3600000);
                tokenRepository.save(jwtToken);

                return new AuthResponseDTO(token);
            }
        } catch (Exception e) {
            throw new AuthenticationFailedException("Invalid username or password", e);
        }
        throw new AuthenticationFailedException("Authentication failed for unknown reasons");
    }

    @Transactional
    public ResponseEntity<ResponseDTO<String>> logout(String token) {
        if (!tokenRepository.existsByToken(token)) {
            throw new ResourceNotFoundException("Token not found for invalidation");
        }
        tokenRepository.deleteByToken(token);
        String data="Token "+ token+" invalidated successfully";
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(HttpStatus.NO_CONTENT.value(), "Logout successful", data));
//        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO("Logout successful"));
    }
}


//package com.spring.security.service;
//
//import com.spring.security.dto.AppUserDTO;
//import com.spring.security.dto.AuthResponseDTO;
//import com.spring.security.dto.LoginRequestDTO;
//import com.spring.security.enums.RoleEnum;
//import com.spring.security.exception.AuthenticationFailedException;
//import com.spring.security.exception.InvalidRoleException;
//import com.spring.security.exception.ResourceNotFoundException;
//import com.spring.security.exception.UsernameAlreadyExistsException;
//import com.spring.security.model.AppUser;
//import com.spring.security.model.Role;
//
//import com.spring.security.model.JwtToken;
//import com.spring.security.model.UserRole;
//import com.spring.security.repository.AppUserRepository;
//import com.spring.security.repository.JwtTokenRepository;
//import com.spring.security.repository.RoleRepository;
//import com.spring.security.repository.UserRoleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final AppUserRepository userRepository;
//    private final JwtTokenRepository tokenRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//    private final RoleRepository roleRepository; // Add Role repository
//    private final UserRoleRepository userRoleRepository; // Add UserRole repository
//
//
//    @Transactional
//    public ResponseEntity<AuthResponseDTO> register(AppUserDTO userDTO) {
//        if (userRepository.existsByUsername(userDTO.getUsername())) {
//            throw new UsernameAlreadyExistsException("Username already exists");
//        }
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            throw new UsernameAlreadyExistsException("Email already exists");
//        }
//
//
//        Role userRole = roleRepository.findByName(RoleEnum.USER)
//                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
//
//
//        AppUser user = new AppUser();
//        user.setUsername(userDTO.getUsername());
//        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
//        user.setPassword(hashedPassword);
//        user.setEmail(userDTO.getEmail());
//        user.setCreatedAt(LocalDateTime.now());
//        user.setActive(true);
//
//        AppUser savedUser =userRepository.save(user);
//
//        UserRole userRoleAssociation = new UserRole();
//        userRoleAssociation.setUser(savedUser);
//        userRoleAssociation.setRole(userRole);
//        userRoleRepository.save(userRoleAssociation);
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
//
//        String token = jwtService.generateToken(userDetails);
//
//
//        JwtToken jwtToken = new JwtToken(null, user.getId(), token, System.currentTimeMillis() + 3600000);
//        tokenRepository.save(jwtToken);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
//
//    }
//
//
//    @Transactional
//    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
//
//            if (authentication.isAuthenticated()) {
//                // Retrieve UserDetails from the authentication
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//
//                String token = jwtService.generateToken(userDetails);
//
//                // Save token in the database
//                AppUser appUser = userRepository.findByUsername(loginRequestDTO.getUsername())
//                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//                JwtToken jwtToken = new JwtToken(null, appUser.getId(), token, System.currentTimeMillis() + 3600000);
//
//                tokenRepository.save(jwtToken);
//
//
//                return new AuthResponseDTO(token);
//            }
//        } catch (Exception e) {
//            throw new AuthenticationFailedException("Invalid username or password", e);
//        }
//        throw new AuthenticationFailedException("Authentication failed for unknown reasons");
//    }
//
//    @Transactional
//    public ResponseEntity<String> logout(String token) {
//        if (!tokenRepository.existsByToken(token)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found for invalidation");
//        }
//        tokenRepository.deleteByToken(token);
//        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out and token invalidated");
//    }
//}
