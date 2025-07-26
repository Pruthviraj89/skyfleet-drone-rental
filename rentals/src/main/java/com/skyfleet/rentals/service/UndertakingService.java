package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Undertaking;
import java.util.List;

public interface UndertakingService {
    Undertaking saveUndertaking(Undertaking undertaking);
    List<Undertaking> getAllUndertakings();
    Undertaking getUndertakingById(Long id);
    void deleteUndertaking(Long id);
}