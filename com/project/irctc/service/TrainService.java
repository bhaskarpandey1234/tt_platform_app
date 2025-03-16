package com.project.irctc.service;

import com.project.irctc.dto.*;
import com.project.irctc.dto.request.*;
import com.project.irctc.dto.response.*;
import com.project.irctc.exception.ResourceNotFoundException;
import com.project.irctc.model.*;
import com.project.irctc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.project.irctc.model.Train;
import com.project.irctc.repository.TrainRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface TrainService {

    Page<TrainResponseDTO> getTrains(TrainRequestDTO requestDTO);

    ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequest);

    TrainDTO createTrain(TrainDTO trainRequest);

    RouteResponseDTO createRoute(RouteRequestDTO routeRequest);

    StationResponseDTO createStation(StationRequestDTO stationRequestDTO);

    TrainRouteResponseDTO createTrainRoute(TrainRouteRequestDTO trainRouteRequest);
}


//package com.project.irctc.service;
//
//import com.project.irctc.model.Route;
//import com.project.irctc.model.Schedule;
//import com.project.irctc.model.Station;
//import com.project.irctc.model.Train;
//import com.project.irctc.repository.RouteRepository;
//import com.project.irctc.repository.ScheduleRepository;
//import com.project.irctc.repository.StationRepository;
//import com.project.irctc.repository.TrainRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@Service
//@RequiredArgsConstructor
//public class TrainService {
//
//    private final TrainRepository trainRepository;
//
//    private final RouteRepository routeRepository;
//    private final ScheduleRepository scheduleRepository;
//    private final StationRepository stationRepository;
//
//    public Train createTrain(Train train) {
//        return trainRepository.save(train);
//    }
//
//    public Schedule createSchedule(Long trainId, Long routeId, LocalDate departureDate, LocalTime departureTime) {
//        // Fetch Train and Route by ID
//        Train train = trainRepository.findById(trainId)
//                .orElseThrow(() -> new IllegalArgumentException("Train ID not found: " + trainId));
//        Route route = routeRepository.findById(routeId)
//                .orElseThrow(() -> new IllegalArgumentException("Route ID not found: " + routeId));
//
//        // Create and save schedule
//        Schedule schedule = new Schedule();
//        schedule.setTrain(train);
//        schedule.setRoute(route);
//        schedule.setDepartureDate(departureDate);
//        schedule.setAvailableSeats(train.getTotalSeats());
//
//        return scheduleRepository.save(schedule);
//    }
//
//
//    public Route createRoute(Long stationFromId, Long stationToId, double distance) {
//        // Fetch stations by ID
//        Station stationFrom = stationRepository.findById(stationFromId)


//                .orElseThrow(() -> new IllegalArgumentException("Station From ID not found: " + stationFromId));
//        Station stationTo = stationRepository.findById(stationToId)
//                .orElseThrow(() -> new IllegalArgumentException("Station To ID not found: " + stationToId));
//
//        // Create and save route
//        Route route = new Route();
//        route.setFromStation(stationFrom);
//        route.setToStation(stationTo);
//        route.setDistance(distance);
//
//        return routeRepository.save(route);
//    }
//
//    public Station createStation(Station station) {
//        return stationRepository.save(station);
//    }
//
//    public Page<Train> getTrains(int page, int size, String fromStation, String toStation, LocalDate date) {
//        Pageable pageable = PageRequest.of(page, size);
//        return trainRepository.findAllWithFiltersNative(fromStation, toStation, date, pageable);
//    }
//}
//
