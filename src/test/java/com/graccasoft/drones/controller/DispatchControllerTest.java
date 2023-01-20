package com.graccasoft.drones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.enums.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 3:32 pm
 */
@WebMvcTest(DispatchController.class)
class DispatchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldThrowAnExceptionWhenCreatingDroneWithInvalidInput() throws Exception {

        this.mockMvc.perform(post("/api/v1/dispatch/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(new Drone()) )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateNewDroneWithValidInput() throws Exception {
        Drone drone = new Drone();
        drone.setState(DroneState.IDLE);
        drone.setWeightLimit(new BigDecimal(300));
        drone.setSerialNumber("1002023");
        this.mockMvc.perform(post("/api/v1/dispatch/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(drone) )
                )
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}