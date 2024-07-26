package com.camp.reservation.reservation.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationResponseDTO {
    private Long id;
    private String userName;
    private String userFaculty;
    private Long roomId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private String groupname;
    private String purpose;
}
