package com.camp.reservation.reservation.room.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RoomAvailabilityRequestDTO {
    private int capacity;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
