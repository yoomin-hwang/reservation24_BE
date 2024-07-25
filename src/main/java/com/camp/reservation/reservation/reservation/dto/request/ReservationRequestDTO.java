package com.camp.reservation.reservation.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequestDTO {
    private Long userId;
    private Long roomId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private String groupname;
    private String purpose;
}
