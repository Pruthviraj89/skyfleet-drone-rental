package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.dto.PenaltyRequestDTO;
import com.skyfleet.rentals.dto.PenaltyResponseDTO;
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
    public ResponseEntity<PenaltyResponseDTO> createPenalty(@RequestBody PenaltyRequestDTO penalty) {
        return ResponseEntity.ok(penaltyService.savePenalty(penalty));
    }

    @GetMapping
    public ResponseEntity<List<PenaltyResponseDTO>> getAllPenalties() {
        return ResponseEntity.ok(penaltyService.getAllPenalties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenaltyResponseDTO> getPenaltyById(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(penaltyService.getPenaltyById(id));
      
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Long id) {
        penaltyService.deletePenalty(id);
        return ResponseEntity.noContent().build();
    }
}