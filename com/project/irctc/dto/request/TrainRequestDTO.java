package com.project.irctc.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainRequestDTO {
    private int page = 0;
    private int size = 10;
    private String fromStation;
    private String toStation;
    private LocalDate date;
}
