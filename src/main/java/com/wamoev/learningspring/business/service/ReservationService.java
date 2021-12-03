package com.wamoev.learningspring.business.service;

import com.wamoev.learningspring.business.domain.RoomReservation;
import com.wamoev.learningspring.data.entity.Guest;
import com.wamoev.learningspring.data.entity.Reservation;
import com.wamoev.learningspring.data.entity.Room;
import com.wamoev.learningspring.data.repository.GuestRepository;
import com.wamoev.learningspring.data.repository.ReservationRepository;
import com.wamoev.learningspring.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(GuestRepository guestRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getReservationForDate(Date date) {
        Iterable<Room> rooms = roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });

        Iterable<Reservation> reservations = reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setGuestId(guest.getGuestId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
        });

        ArrayList<RoomReservation> result = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            result.add(roomReservationMap.get(id));
        }

        return result;
    }
}
