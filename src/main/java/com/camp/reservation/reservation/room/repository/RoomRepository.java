package com.camp.reservation.reservation.room.repository;

import com.camp.reservation.reservation.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.capacity >= :capacity " +
            "AND NOT EXISTS (SELECT res FROM Reservation res " +
            "WHERE res.room = r " +
            "AND res.date = :reservationDate " +
            "AND (res.startTime < :endTime AND res.endTime > :startTime))")
    List<Room> findAvailableRooms(@Param("capacity") int capacity,
                                  @Param("reservationDate") LocalDate reservationDate,
                                  @Param("startTime") LocalTime startTime,
                                  @Param("endTime") LocalTime endTime);
}
