package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Penalty;
import java.util.List;

public interface PenaltyService {
    Penalty savePenalty(Penalty penalty);
    List<Penalty> getAllPenalties();
    Penalty getPenaltyById(Long id);
    void deletePenalty(Long id);
}