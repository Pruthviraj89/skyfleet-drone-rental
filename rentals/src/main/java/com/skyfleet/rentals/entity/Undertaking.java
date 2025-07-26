package com.skyfleet.rentals.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "undertakings")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Undertaking  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonIgnore
    private Booking booking;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @Column(name = "deposit_amount", nullable = false)
    private Double depositAmount;

    @Column(name = "damage_clause_text", nullable = false)
    private String damageClauseText;
}