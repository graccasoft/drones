package com.graccasoft.drones.service;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;

import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:33 pm
 */
public interface DispatchService {
    Drone saveDrone(Drone drone);
    void loadDrone(Integer droneId, List<Medication> medicationItems);
    List<Medication> getDroneMedicationList(Integer droneId);
    List<Drone> getAvailableDrones();
    BatteryLevelResponse getDroneBatteryLevel(Integer droneId);
}
