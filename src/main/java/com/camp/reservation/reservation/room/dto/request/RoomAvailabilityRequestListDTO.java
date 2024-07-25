package com.camp.reservation.reservation.room.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomAvailabilityRequestListDTO {
    private List<RoomAvailabilityRequestDTO> requests;
}
