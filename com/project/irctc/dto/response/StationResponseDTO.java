package com.project.irctc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StationResponseDTO {
    private Long id; // Station ID to update
    private String name; // Optional: New name of the station
    private String city; // Optional: New city
    private String state; // Optional: New state
    private String country;
}

