package com.project.irctc.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class JwtToken {

    @Id
    private String id;
    private Long userId;
    private String token;
    private long expirationTime;

}
