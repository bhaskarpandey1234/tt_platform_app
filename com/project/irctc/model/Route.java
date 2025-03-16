package com.project.irctc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double distance;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station fromStation; // Starting station

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station toStation; // Destination station

}

