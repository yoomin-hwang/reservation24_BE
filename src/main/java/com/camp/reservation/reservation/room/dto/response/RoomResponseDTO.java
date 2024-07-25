package com.camp.reservation.reservation.room.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDTO {
    private Long id;
    private String name;
    private int capacity;
    private String image;
    private String extraInfo;
}
