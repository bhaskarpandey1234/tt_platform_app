package com.project.irctc.dto.request;

import lombok.Data;

@Data
public class RouteRequestDTO {
    private Long stationFromId;
    private Long stationToId;
    private double distance;
}
