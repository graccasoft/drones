package com.graccasoft.drones.service;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.enums.DroneState;
import com.graccasoft.drones.exception.DroneNotFoundException;
import com.graccasoft.drones.exception.DroneCanNotBeLoadedException;
import com.graccasoft.drones.repository.DroneRepository;
import com.graccasoft.drones.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:36 pm
 */
@Service
public class DispatchServiceImpl implements DispatchService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DispatchServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    @Transactional
    public Drone saveDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    @Transactional
    public void loadDrone(Integer droneId, List<Medication> medicationItems) {
        if(!droneRepository.existsById(droneId) ){
            throw new DroneNotFoundException("Drone with provided id could not be found");
        }

        Drone drone = droneRepository.getReferenceById(droneId);

        //% should be greater than 25 %
        if( drone.getPercentage() == null || drone.getPercentage().compareTo(new BigDecimal(25)) < 0 ){
            throw new DroneCanNotBeLoadedException("Drone can not be loaded, battery is below 25%");
        }
        if( !drone.getState().equals(DroneState.IDLE) ){
            throw new DroneCanNotBeLoadedException("Drone can not be loaded, it is not IDLE");
        }
        medicationItems.forEach(medication -> {
            drone.getLoadedMedication().add( medicationRepository.save(medication) );
        });



        //check loaded total weight
        BigDecimal totalLoadedWeight = BigDecimal.ZERO;
        for(Medication medication : drone.getLoadedMedication()){
            totalLoadedWeight = totalLoadedWeight.add(medication.getWeight());
        }
        //throw exception if weight exceeds the linit
        if( totalLoadedWeight.compareTo(drone.getWeightLimit()) == 1 ){
            throw new DroneCanNotBeLoadedException("The weight of provided medication is over the limit for this drone");
        }

        //update status of drone
        drone.setState(DroneState.LOADING);
        droneRepository.save(drone);


    }

    @Override
    public List<Medication> getDroneMedicationList(Integer droneId) {
        //check if drone with provided id exists
        if(!droneRepository.existsById(droneId) ){
            throw new DroneNotFoundException("Drone with provided id could not be found");
        }
        Drone drone = droneRepository.getReferenceById(droneId);
        return drone.getLoadedMedication();
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.getDronesByState(DroneState.IDLE);
    }

    @Override
    public BatteryLevelResponse getDroneBatteryLevel(Integer droneId) {
        //check if drone with given id exists
        if(!droneRepository.existsById(droneId) ){
            throw new DroneNotFoundException("Drone with provided id could not be found");
        }
        Drone drone = droneRepository.getReferenceById(droneId);
        return new BatteryLevelResponse("Drone battery level", drone.getPercentage());
    }
}
