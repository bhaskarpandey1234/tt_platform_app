package com.project.irctc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private String username;

    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
}
