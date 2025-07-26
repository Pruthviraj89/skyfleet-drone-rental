package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.entity.Undertaking;
import com.skyfleet.rentals.service.UndertakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/undertakings")
public class UndertakingController {

    @Autowired
    private UndertakingService undertakingService;

    @PostMapping
    public ResponseEntity<Undertaking> createUndertaking(@RequestBody Undertaking undertaking) {
        return ResponseEntity.ok(undertakingService.saveUndertaking(undertaking));
    }

    @GetMapping
    public ResponseEntity<List<Undertaking>> getAllUndertakings() {
        return ResponseEntity.ok(undertakingService.getAllUndertakings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Undertaking> getUndertakingById(@PathVariable Long id) {
        Undertaking undertaking = undertakingService.getUndertakingById(id);
        return undertaking != null ? ResponseEntity.ok(undertaking) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUndertaking(@PathVariable Long id) {
        undertakingService.deleteUndertaking(id);
        return ResponseEntity.noContent().build();
    }
}