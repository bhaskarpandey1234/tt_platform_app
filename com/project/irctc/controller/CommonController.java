package com.project.irctc.controller;

import com.project.irctc.dto.response.ResponseDTO;
import com.project.irctc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/resource")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
@RequiredArgsConstructor

public class CommonController {

    private final UserService userService;

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<String>> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            return userService.logout(jwtToken);
        }
        return ResponseEntity.badRequest().body(
                new ResponseDTO<>(400, "Invalid token format", null)
        );
    }
}
