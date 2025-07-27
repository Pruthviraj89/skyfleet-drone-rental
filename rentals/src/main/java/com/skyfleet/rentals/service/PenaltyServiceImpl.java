package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Penalty;
import com.skyfleet.rentals.entity.PenaltyStatus;
import com.skyfleet.rentals.repository.PenaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Override
    public Penalty savePenalty(Penalty penalty) {
    	if (penalty.getPenaltyStatus() == null) {
            penalty.setPenaltyStatus(PenaltyStatus.PENDING);
        }
        return penaltyRepository.save(penalty);
    }

    @Override
    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    @Override
    public Penalty getPenaltyById(Long id) {
        return penaltyRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePenalty(Long id) {
        penaltyRepository.deleteById(id);
    }
}