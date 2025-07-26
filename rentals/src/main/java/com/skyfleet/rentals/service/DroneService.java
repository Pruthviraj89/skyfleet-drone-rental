package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Drone;
import java.util.List;

public interface DroneService {
    Drone saveDrone(Drone drone);
    List<Drone> getAllDrones();
    Drone getDroneById(Long id);
    void deleteDrone(Long id);
}