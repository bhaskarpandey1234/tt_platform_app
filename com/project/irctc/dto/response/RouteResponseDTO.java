package com.project.irctc.dto.response;

import lombok.Data;

@Data
public class RouteResponseDTO {
    private Long routeId;
    private String fromStation;
    private String toStation;
    private double distance;
}
