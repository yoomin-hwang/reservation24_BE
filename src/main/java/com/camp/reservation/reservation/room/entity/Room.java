package com.camp.reservation.reservation.room.entity;

import com.camp.reservation.reservation.room.entity.RoomAvailability;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int capacity;

    private String image;

    private String extraInfo;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomAvailability> availability = new ArrayList<>();

    @Builder
    public Room(String name, int capacity, String image, String extraInfo) {
        this.name = name;
        this.capacity = capacity;
        this.image = "/logo192.png";
        this.extraInfo = extraInfo;

        RoomAvailability initialAvailability = RoomAvailability.builder()
                .room(this)
                .date(LocalDate.now())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .build();
        this.availability.add(initialAvailability);
    }
}
