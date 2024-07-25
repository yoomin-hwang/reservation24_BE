package com.camp.reservation.reservation.room.service;

import com.camp.reservation.reservation.room.dto.request.RoomAvailabilityRequestDTO;
import com.camp.reservation.reservation.room.entity.Room;
import com.camp.reservation.reservation.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll().stream().map(room -> room).collect(Collectors.toList());
    }

    public List<Room> findAvailableRooms(List<RoomAvailabilityRequestDTO> requests) {
        if (requests == null) {
            System.out.println("Received null requests");
            return Collections.emptyList();
        }
        return requests.stream()
                .flatMap(request -> roomRepository.findAvailableRooms(
                        request.getCapacity(),
                        request.getReservationDate(),
                        request.getStartTime(),
                        request.getEndTime()
                ).stream())
                .distinct()
                .collect(Collectors.toList());
    }



    public Optional<Room> findRoomById(Long id) {
        return roomRepository.findById(id);
    }
}
