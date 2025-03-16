package com.project.irctc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate departureDate;

//    private List<String> daysOfOperation;

//    @Column(nullable = false)
    private LocalTime departureTime; // Departure time

//    @Column(nullable = false)
    private LocalDateTime arrivalTime; // Arrival time

    @ManyToOne
    @JoinColumn(nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Route route;

    @Column(nullable = false)
    private Integer availableSeats; // Seats available on this schedule



}

