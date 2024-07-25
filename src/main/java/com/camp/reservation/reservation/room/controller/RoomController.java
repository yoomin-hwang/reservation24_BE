package com.camp.reservation.reservation.room.controller;

import com.camp.reservation.reservation.room.dto.request.RoomAvailabilityRequestListDTO;
import com.camp.reservation.reservation.room.dto.response.RoomResponseDTO;
import com.camp.reservation.reservation.room.entity.Room;
import com.camp.reservation.reservation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/available")
    public List<RoomResponseDTO> findAvailableRooms(@RequestBody RoomAvailabilityRequestListDTO requestList) {
        List<Room> availableRooms = roomService.findAvailableRooms(requestList.getRequests());
        return availableRooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long roomId) {
        Optional<Room> roomOptional = roomService.findRoomById(roomId);
        return roomOptional.map(room -> ResponseEntity.ok(convertToDTO(room)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private RoomResponseDTO convertToDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setImage(room.getImage());
        dto.setExtraInfo(room.getExtraInfo());
        return dto;
    }
}
