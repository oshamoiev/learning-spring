package com.wamoev.learningspring.web;

import com.wamoev.learningspring.business.domain.RoomReservation;
import com.wamoev.learningspring.business.service.ReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebMvcTest(RoomReservationController.class)
public class RoomReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getReservations() throws Exception {
        Date date = DateUtils.createDateFromDateString("2021-01-01");

        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoomId(1);
        roomReservation.setLastName("Sha");
        roomReservation.setFirstName("Ol");
        roomReservation.setDate(date);
        roomReservation.setGuestId(1);
        roomReservation.setRoomNumber("100");
        roomReservation.setRoomName("Name");

        List<RoomReservation> roomReservations = new ArrayList<>();
        roomReservations.add(roomReservation);

        BDDMockito.given(reservationService.getReservationForDate(date)).willReturn(roomReservations);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations?date=2021-01-01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Sha, Ol")));
    }
}