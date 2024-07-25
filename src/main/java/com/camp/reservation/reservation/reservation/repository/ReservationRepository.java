package com.camp.reservation.reservation.reservation.repository;

import com.camp.reservation.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
