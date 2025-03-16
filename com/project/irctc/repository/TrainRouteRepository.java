package com.project.irctc.repository;

import com.project.irctc.model.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {
}
