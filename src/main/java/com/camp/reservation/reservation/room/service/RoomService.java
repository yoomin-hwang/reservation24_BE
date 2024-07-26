package com.camp.reservation.reservation.room.service;

import com.camp.reservation.reservation.room.dto.request.RoomAvailabilityRequestDTO;
import com.camp.reservation.reservation.room.entity.Room;
import com.camp.reservation.reservation.room.entity.RoomAvailability;
import com.camp.reservation.reservation.room.repository.RoomRepository;
import com.camp.reservation.reservation.room.repository.RoomAvailabilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> findAvailableRooms(RoomAvailabilityRequestDTO request) {
        if (request == null) {
            return Collections.emptyList(); // request가 null인 경우 빈 리스트 반환
        }
        return roomRepository.findAvailableRooms(
                        request.getCapacity(),
                        request.getReservationDate(),
                        request.getStartTime(),
                        request.getEndTime()
                )
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

//    private void initializeRoomAvailability(Room room) {
//        List<RoomAvailability> availabilities = new ArrayList<>();
//        LocalDate currentDate = LocalDate.now();
//        LocalDate endDate = currentDate.plusMonths(1); // 한 달 뒤까지 가용 시간 초기화
//
//        while (!currentDate.isAfter(endDate)) {
//            RoomAvailability availability = RoomAvailability.builder()
//                    .room(room)
//                    .date(currentDate)
//                    .startTime(LocalTime.of(0, 0)) // 하루 24시간 전체를 가용 시간으로 설정
//                    .endTime(LocalTime.of(23, 59))
//                    .build();
//            availabilities.add(availability);
//            currentDate = currentDate.plusDays(1);
//        }
//
//        roomAvailabilityRepository.saveAll(availabilities);
//    }

    @Transactional
    public void updateRoomAvailability(Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<RoomAvailability> availabilities = roomAvailabilityRepository.findByRoomAndDate(room, date);
        List<RoomAvailability> updatedAvailabilities = new ArrayList<>();

        for (RoomAvailability availability : availabilities) {
            if (startTime.isAfter(availability.getEndTime()) || endTime.isBefore(availability.getStartTime())) {
                // 예약 시간이 가용 시간과 겹치지 않는 경우 그대로 추가
                updatedAvailabilities.add(availability);
            } else {
                if (startTime.isAfter(availability.getStartTime())) {
                    // 예약 시작 시간이 가용 시간 안에 있는 경우
                    RoomAvailability newAvailability = RoomAvailability.builder()
                            .room(room)
                            .date(date)
                            .startTime(availability.getStartTime())
                            .endTime(startTime)
                            .build();
                    updatedAvailabilities.add(newAvailability);
                }
                if (endTime.isBefore(availability.getEndTime())) {
                    // 예약 종료 시간이 가용 시간 안에 있는 경우
                    RoomAvailability newAvailability = RoomAvailability.builder()
                            .room(room)
                            .date(date)
                            .startTime(endTime)
                            .endTime(availability.getEndTime())
                            .build();
                    updatedAvailabilities.add(newAvailability);
                }
            }
        }

        roomAvailabilityRepository.deleteAll(availabilities);
        roomAvailabilityRepository.saveAll(updatedAvailabilities);
    }
}
