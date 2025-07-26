package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<Drone> createDrone(@RequestBody Drone drone) {
        return ResponseEntity.ok(droneService.saveDrone(drone));
    }

    @GetMapping
    public ResponseEntity<List<Drone>> getAllDrones() {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable Long id) {
        Drone drone = droneService.getDroneById(id);
        return drone != null ? ResponseEntity.ok(drone) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrone(@PathVariable Long id) {
        droneService.deleteDrone(id);
        return ResponseEntity.noContent().build();
    }
}