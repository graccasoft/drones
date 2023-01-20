package com.graccasoft.drones.repository;

import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:21 pm
 */
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    List<Drone> getDronesByState(DroneState state);
}
