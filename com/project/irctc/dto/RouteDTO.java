package com.project.irctc.dto;

import lombok.Data;

@Data
public class RouteDTO {
    private Long id;
    private String fromStation;
    private String toStation;
    private double distance;

    public RouteDTO(Long id, String fromStation, String toStation, double distance) {
        this.id = id;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.distance = distance;
    }
}
