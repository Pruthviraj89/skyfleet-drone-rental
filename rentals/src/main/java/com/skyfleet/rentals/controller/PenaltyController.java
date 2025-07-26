package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.entity.Penalty;
import com.skyfleet.rentals.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalties")
public class PenaltyController {

    @Autowired
    private PenaltyService penaltyService;

    @PostMapping
    public ResponseEntity<Penalty> createPenalty(@RequestBody Penalty penalty) {
        return ResponseEntity.ok(penaltyService.savePenalty(penalty));
    }

    @GetMapping
    public ResponseEntity<List<Penalty>> getAllPenalties() {
        return ResponseEntity.ok(penaltyService.getAllPenalties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Penalty> getPenaltyById(@PathVariable Long id) {
        Penalty penalty = penaltyService.getPenaltyById(id);
        return penalty != null ? ResponseEntity.ok(penalty) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Long id) {
        penaltyService.deletePenalty(id);
        return ResponseEntity.noContent().build();
    }
}