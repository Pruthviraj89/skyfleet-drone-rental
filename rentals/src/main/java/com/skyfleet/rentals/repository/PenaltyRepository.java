package com.skyfleet.rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfleet.rentals.entity.Penalty;

public interface PenaltyRepository extends JpaRepository<Penalty, Long>{

}
