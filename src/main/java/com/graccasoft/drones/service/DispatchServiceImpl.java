package com.graccasoft.drones.service;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.enums.DroneState;
import com.graccasoft.drones.exception.DroneNotFoundException;
import com.graccasoft.drones.exception.DroneReachedWeightLimitException;
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
            throw new DroneReachedWeightLimitException("The weight of provided medication is over the limit for this drone");
        }

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
