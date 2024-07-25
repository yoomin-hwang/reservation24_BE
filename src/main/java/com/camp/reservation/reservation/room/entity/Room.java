package com.camp.reservation.reservation.room.entity;

import com.camp.reservation.reservation.room.entity.RoomAvailability;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

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

    private String image = "/logo192.png";

    private String extraInfo;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomAvailability> availability;

    @Builder
    public Room(String name, int capacity, String image, String extraInfo) {
        this.name = name;
        this.capacity = capacity;
        this.image = (image != null) ? image : "/logo192.png";
        this.extraInfo = extraInfo;
    }
}
