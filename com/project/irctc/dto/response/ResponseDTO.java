package com.project.irctc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private int status;
    private String message;
    private T data;
}

