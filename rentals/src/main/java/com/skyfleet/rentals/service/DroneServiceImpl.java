package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Override
    public Drone saveDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone getDroneById(Long id) {
        return droneRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDrone(Long id) {
        droneRepository.deleteById(id);
    }
}