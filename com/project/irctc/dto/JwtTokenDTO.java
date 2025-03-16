package com.project.irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class JwtTokenDTO {

    private String id;
    private String token;
}
