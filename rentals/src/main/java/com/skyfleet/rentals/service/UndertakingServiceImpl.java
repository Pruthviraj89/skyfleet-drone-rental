package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Undertaking;
import com.skyfleet.rentals.repository.UndertakingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UndertakingServiceImpl implements UndertakingService {

    @Autowired
    private UndertakingRepository undertakingRepository;

    @Override
    public Undertaking saveUndertaking(Undertaking undertaking) {
        return undertakingRepository.save(undertaking);
    }

    @Override
    public List<Undertaking> getAllUndertakings() {
        return undertakingRepository.findAll();
    }

    @Override
    public Undertaking getUndertakingById(Long id) {
        return undertakingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUndertaking(Long id) {
        undertakingRepository.deleteById(id);
    }
}