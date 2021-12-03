package com.wamoev.learningspring.web;

import com.wamoev.learningspring.business.domain.RoomReservation;
import com.wamoev.learningspring.business.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
    private final ReservationService reservationService;

    public RoomReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations(@RequestParam(name = "date", required = false) String dateString, Model model) {
        Date date = DateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = this.reservationService.getReservationForDate(date);
        model.addAttribute("roomReservations", roomReservations);
        return "reservations";
    }
}
