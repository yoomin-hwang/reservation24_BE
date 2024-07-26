package com.camp.reservation.reservation.reservation.controller;

import com.camp.reservation.reservation.reservation.dto.request.ReservationRequestDTO;
import com.camp.reservation.reservation.reservation.dto.response.ReservationResponseDTO;
import com.camp.reservation.reservation.reservation.entity.Reservation;
import com.camp.reservation.reservation.reservation.service.ReservationService;
//import com.camp.reservation.reservation.user.entity.User;
//import com.camp.reservation.reservation.user.repository.UserRepository;
import com.camp.reservation.reservation.room.entity.Room;
import com.camp.reservation.reservation.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reserve")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO requestDTO) {
        Optional<Room> roomOptional = roomRepository.findById(requestDTO.getRoomId());

        if (roomOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Reservation reservation = Reservation.builder()
                .userName(requestDTO.getUserName())
                .userFaculty(requestDTO.getUserFaculty())
                .room(roomOptional.get())
                .date(requestDTO.getDate())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .capacity(requestDTO.getCapacity())
                .groupname(requestDTO.getGroupname())
                .purpose(requestDTO.getPurpose())
                .build();

        Reservation savedReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(convertToDTO(savedReservation));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid JSON: " + ex.getMessage());
    }

    @GetMapping("/board")
    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservationOptional = reservationService.getReservationById(id);
        return reservationOptional.map(reservation -> ResponseEntity.ok(convertToDTO(reservation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponseDTO convertToDTO(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(reservation.getId());
        dto.setUserName(reservation.getUserName());
        dto.setUserFaculty(reservation.getUserFaculty());
        dto.setRoomId(reservation.getRoom().getId());
        dto.setDate(reservation.getDate());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setCapacity(reservation.getCapacity());
        dto.setGroupname(reservation.getGroupname());
        dto.setPurpose(reservation.getPurpose());
        return dto;
    }
}
