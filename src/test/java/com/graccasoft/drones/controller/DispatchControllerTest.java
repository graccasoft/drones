package com.graccasoft.drones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.enums.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void shouldThrowAnExceptionWhenLoadingMedicationWithInvalidInput() throws Exception {

        Medication medication = new Medication();
        medication.setCode("|--!!!");
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);

        this.mockMvc.perform(post("/api/v1/dispatch/load/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(medication) )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldLoadDroneWhenLoadingMedicationWithValidInput() throws Exception {

        Medication medication = new Medication();
        medication.setCode("1023902");
        medication.setName("Paracetamol");
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);

        this.mockMvc.perform(post("/api/v1/dispatch/load/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(medicationList) )
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionWhenLoadingMedicationHeavierThanDrone() throws Exception {

        Drone drone = new Drone();
        drone.setState(DroneState.IDLE);
        drone.setWeightLimit(new BigDecimal(200));
        drone.setSerialNumber("1002023");

        Medication medication = new Medication();
        medication.setCode("1023902");
        medication.setName("Paracetamol");
        medication.setWeight(new BigDecimal(300));
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);

        this.mockMvc.perform(post("/api/v1/dispatch/load/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(medicationList) )
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