package com.project.irctc.dto;

import lombok.Data;

@Data
public class TrainDTO {
    private Long id;
    private String trainName;
    private String trainNumber;
    private int totalSeats;


}
