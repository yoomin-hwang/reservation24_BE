package com.camp.reservation.reservation.reservation.service;

import com.camp.reservation.reservation.reservation.entity.Reservation;
import com.camp.reservation.reservation.reservation.repository.ReservationRepository;
import com.camp.reservation.reservation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    public Reservation createReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        roomService.updateRoomAvailability(reservation.getRoom(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime());
        return savedReservation;
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            roomService.updateRoomAvailability(reservation.getRoom(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime());
            reservationRepository.deleteById(id);
        }
    }
}
