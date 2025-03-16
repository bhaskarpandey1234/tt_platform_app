package com.project.irctc.dto.request;

import lombok.Data;

@Data
public class StationRequestDTO {
    private Long id; // Station ID to update
    private String name; // Optional: New name of the station
    private String city; // Optional: New city
    private String state; // Optional: New state
    private String country;
}
