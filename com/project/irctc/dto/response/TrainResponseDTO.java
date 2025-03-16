package com.project.irctc.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainResponseDTO {
    private Long id;
    private String trainName;
    private String trainNumber;
    private Integer totalSeats;
    private List<String> fromStations;
    private List<String> toStations;
    private List<LocalDate> departureDates;
}
