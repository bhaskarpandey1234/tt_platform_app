package com.project.irctc.controller;


import com.project.irctc.dto.AppUserDTO;
import com.project.irctc.dto.response.AuthResponseDTO;
import com.project.irctc.dto.request.LoginRequestDTO;
import com.project.irctc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AppUserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }








//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")


//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    @PostMapping("/logout")
//    public ResponseEntity<ResponseDTO<String>> logout(@RequestHeader("Authorization") String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            String jwtToken = token.substring(7); // Remove "Bearer " prefix
//            userService.logout(jwtToken);
//        }
////        return ResponseEntity.ok(new ResponseDTO("Logout successful"));
////        return ResponseEntity.ok().build();
//    }
}
