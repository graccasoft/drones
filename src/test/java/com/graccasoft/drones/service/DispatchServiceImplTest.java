package com.graccasoft.drones.service;

import com.graccasoft.drones.dto.RegisterDroneRequest;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.enums.DroneModel;
import com.graccasoft.drones.enums.DroneState;
import com.graccasoft.drones.exception.DroneCanNotBeLoadedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 6:55 pm
 */
@SpringBootTest
class DispatchServiceImplTest {

    @Autowired
    private DispatchService dispatchService;

    @Test
    void shouldSaveDroneWhenValid(){
        RegisterDroneRequest drone = new RegisterDroneRequest();
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber("01929232");
        drone.setModel(DroneModel.CRUISERWEIGHT);
        drone.setWeightLimit(new BigDecimal(500));

        Assertions.assertNotNull(dispatchService.saveDrone(drone));
    }

    @Test
    void shouldNotLoadDroneIfLoadIsHeavier(){
        RegisterDroneRequest drone = new RegisterDroneRequest();
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber("01929232");
        drone.setModel(DroneModel.CRUISERWEIGHT);
        drone.setPercentage(new BigDecimal(60));
        drone.setWeightLimit(new BigDecimal(500));

        Integer droneId = dispatchService.saveDrone(drone).getId();

        Medication medication1 = new Medication();
        medication1.setName("med1");
        medication1.setWeight(new BigDecimal(300));
        medication1.setCode("med1");

        Medication medication2 = new Medication();
        medication2.setName("med2");
        medication2.setWeight(new BigDecimal(300));
        medication2.setCode("med2");

        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication1);
        medicationList.add(medication2);

        DroneCanNotBeLoadedException thrown = Assertions.assertThrows(DroneCanNotBeLoadedException.class, () -> {
            dispatchService.loadDrone(droneId,medicationList);
        }, "DroneCanNotBeLoadedException was expected");

    }

    @Test
    void shouldNotLoadDroneIPercentageIsLessThan25(){
        RegisterDroneRequest drone = new RegisterDroneRequest();
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber("01929232");
        drone.setModel(DroneModel.CRUISERWEIGHT);
        drone.setPercentage(new BigDecimal(24));
        drone.setWeightLimit(new BigDecimal(500));

        Integer droneId = dispatchService.saveDrone(drone).getId();

        Medication medication1 = new Medication();
        medication1.setName("med1");
        medication1.setWeight(new BigDecimal(300));
        medication1.setCode("med1");

        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication1);
        DroneCanNotBeLoadedException thrown = Assertions.assertThrows(DroneCanNotBeLoadedException.class, () -> {
            dispatchService.loadDrone(droneId,medicationList);
        }, "DroneCanNotBeLoadedException was expected");

    }
    @Test
    void shouldLoadMedicationIfWeightIsInLimit(){
        RegisterDroneRequest drone = new RegisterDroneRequest();
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber("01929232");
        drone.setModel(DroneModel.CRUISERWEIGHT);
        drone.setPercentage(new BigDecimal(75));
        drone.setWeightLimit(new BigDecimal(500));

        Integer droneId = dispatchService.saveDrone(drone).getId();

        Medication medication1 = new Medication();
        medication1.setName("med1");
        medication1.setWeight(new BigDecimal(300));
        medication1.setCode("med1");

        Medication medication2 = new Medication();
        medication2.setName("med2");
        medication2.setWeight(new BigDecimal(200));
        medication2.setCode("med2");

        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication1);
        medicationList.add(medication2);

        dispatchService.loadDrone(droneId,medicationList);

        Assertions.assertEquals(2, dispatchService.getDroneMedicationList(droneId).size());
    }

}