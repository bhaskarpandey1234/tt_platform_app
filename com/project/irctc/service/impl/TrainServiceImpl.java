package com.project.irctc.service.impl;

import com.project.irctc.dto.*;
import com.project.irctc.dto.request.*;
import com.project.irctc.dto.response.*;
import com.project.irctc.exception.ResourceNotFoundException;
import com.project.irctc.model.*;
import com.project.irctc.repository.*;
import com.project.irctc.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.project.irctc.model.Train;
import com.project.irctc.repository.TrainRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final TrainRouteRepository trainRouteRepository;

    public Page<TrainResponseDTO> getTrains(TrainRequestDTO requestDTO) {
        int page = requestDTO.getPage();
        int size = requestDTO.getSize();
        int offset = page * size;

        LocalDate date = requestDTO.getDate();
        String formattedDate = date != null ? date.toString() : null;

        // Normalize fromStation and toStation using your logic encapsulated in a helper method
        String fromStation = normalizeSearchText(requestDTO.getFromStation());
        String toStation = normalizeSearchText(requestDTO.getToStation());

        // Fetch the paginated trains
        List<Train> trains = trainRepository.findAllWithFiltersAndSearch(
                fromStation,
                toStation,
                formattedDate,
                size,
                offset
        );

        // Count total trains for pagination metadata
        long totalTrains = trainRepository.countAllWithFiltersAndSearch(
                fromStation,
                toStation,
                formattedDate
        );

        // Map Train entities to TrainResponseDTO
        List<TrainResponseDTO> responseDTOs = trains.stream().map(train -> {
            TrainResponseDTO responseDTO = new TrainResponseDTO();
            responseDTO.setId(train.getId());
            responseDTO.setTrainName(train.getTrainName());
            responseDTO.setTrainNumber(train.getTrainNumber());
            responseDTO.setTotalSeats(train.getTotalSeats());

            responseDTO.setFromStations(
                    train.getTrainRoutes().stream()
                            .map(trainRoute -> trainRoute.getRoute().getFromStation().getName())
                            .collect(Collectors.toList())
            );
            responseDTO.setToStations(
                    train.getTrainRoutes().stream()
                            .map(trainRoute -> trainRoute.getRoute().getToStation().getName())
                            .collect(Collectors.toList())
            );
            responseDTO.setDepartureDates(
                    train.getSchedules().stream()
                            .map(Schedule::getDepartureDate)
                            .collect(Collectors.toList())
            );

            return responseDTO;
        }).collect(Collectors.toList());

        // Create and return the Page object
        return new PageImpl<>(responseDTOs, PageRequest.of(page, size), totalTrains);
    }
    private String normalizeSearchText(String searchText) {
        if (searchText != null && !searchText.isEmpty()) {
            return searchText.replaceAll("\\s+", "").toLowerCase();
        }
        return null;
    }


    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequest) {
        Train train = trainRepository.findById(scheduleRequest.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found"));
        Route route = routeRepository.findById(scheduleRequest.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));

        Schedule schedule = new Schedule();
        schedule.setTrain(train);
        schedule.setRoute(route);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setAvailableSeats(train.getTotalSeats());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDTO response = new ScheduleResponseDTO();
        response.setScheduleId(savedSchedule.getId());
        response.setTrainName(savedSchedule.getTrain().getTrainName());
        response.setRouteDetails(savedSchedule.getRoute().getFromStation().getName() + " -> " +
                savedSchedule.getRoute().getToStation().getName());
        response.setDepartureDate(savedSchedule.getDepartureDate());
        response.setDepartureTime(savedSchedule.getDepartureTime());
        return response;
    }
    public TrainDTO createTrain(TrainDTO trainRequest) {
        Train train = new Train();
        train.setTrainName(trainRequest.getTrainName());
        train.setTrainNumber(trainRequest.getTrainNumber());
        train.setTotalSeats(trainRequest.getTotalSeats());
        Train savedTrain = trainRepository.save(train);

        TrainDTO response = new TrainDTO();
        response.setId(savedTrain.getId());
        response.setTrainName(savedTrain.getTrainName());
        response.setTrainNumber(savedTrain.getTrainNumber());
        response.setTotalSeats(savedTrain.getTotalSeats());
        return response;
    }
    public RouteResponseDTO createRoute(RouteRequestDTO routeRequest) {
        Station stationFrom = stationRepository.findById(routeRequest.getStationFromId())
                .orElseThrow(() -> new ResourceNotFoundException("Station From not found"));
        Station stationTo = stationRepository.findById(routeRequest.getStationToId())
                .orElseThrow(() -> new ResourceNotFoundException("Station To not found"));

        Route route = new Route();
        route.setFromStation(stationFrom);
        route.setToStation(stationTo);
        route.setDistance(routeRequest.getDistance());
        Route savedRoute = routeRepository.save(route);

        RouteResponseDTO response = new RouteResponseDTO();
        response.setRouteId(savedRoute.getId());
        response.setFromStation(savedRoute.getFromStation().getName());
        response.setToStation(savedRoute.getToStation().getName());
        response.setDistance(savedRoute.getDistance());
        return response;
    }
    public StationResponseDTO createStation(StationRequestDTO stationRequestDTO) {
        Station station = new Station();
        station.setName(stationRequestDTO.getName());
        station.setCity(stationRequestDTO.getCity());
        station.setState(stationRequestDTO.getState());
        station.setCountry(stationRequestDTO.getCountry());

        Station savedStation = stationRepository.save(station);

        return new StationResponseDTO(
                savedStation.getId(),
                savedStation.getName(),
                savedStation.getCity(),
                savedStation.getState(),
                savedStation.getCountry()
        );
    }

    public TrainRouteResponseDTO createTrainRoute(TrainRouteRequestDTO trainRouteRequest) {
        Train train = trainRepository.findById(trainRouteRequest.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found"));
        Route route = routeRepository.findById(trainRouteRequest.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));

        TrainRoute trainRoute = new TrainRoute();
        trainRoute.setTrain(train);
        trainRoute.setRoute(route);
        TrainRoute savedTrainRoute = trainRouteRepository.save(trainRoute);

        TrainRouteResponseDTO response = new TrainRouteResponseDTO();
        response.setId(savedTrainRoute.getId());
        response.setTrainId(savedTrainRoute.getTrain().getId());
        response.setRouteId(savedTrainRoute.getRoute().getId());
        return response;
    }
}
