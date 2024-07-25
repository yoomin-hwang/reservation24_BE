package com.camp.reservation.reservation.room.repository;

import com.camp.reservation.reservation.room.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    @Query("SELECT ra.room FROM RoomAvailability ra " +
            "WHERE ra.availableDate = :reservationDate " +
            "AND ra.availableStartTime <= :startTime " +
            "AND ra.availableEndTime >= :endTime " +
            "AND ra.room.capacity >= :capacity " +
            "AND NOT EXISTS (SELECT res FROM Reservation res " +
            "WHERE res.room = ra.room " +
            "AND res.date = :date " +
            "AND (res.startTime < :endTime AND res.endTime > :startTime))")
    List<Room> findAvailableRooms(@Param("capacity") int capacity,
                                  @Param("reservationDate") LocalDate reservationDate,
                                  @Param("startTime") LocalTime startTime,
                                  @Param("endTime") LocalTime endTime);
}
