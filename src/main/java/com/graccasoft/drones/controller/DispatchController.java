package com.graccasoft.drones.controller;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.dto.StandardResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 3:13 pm
 */
@RestController
@RequestMapping("/api/v1/dispatch")
public class DispatchController {

    private final DispatchService dispatchService;

    //DispatchService dependency will be auto injected by Spring
    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    //register drone endpoint
    @PostMapping("register")
    public Drone registerDrone(@Valid @RequestBody Drone drone){
        return dispatchService.saveDrone(drone);
    }

    //load medication to drone endpoint
    @PostMapping("load/{droneId}")
    public StandardResponse loadDrone(@Valid @RequestBody List<Medication> medicationItems, @PathVariable Integer droneId){
        dispatchService.loadDrone(droneId, medicationItems);
        return new StandardResponse(200,"Drone loaded");
    }

    //fetch medication loaded to drone endpoint
    @GetMapping("loaded/{droneId}")
    public List<Medication> getLoadedMedication(@PathVariable Integer droneId){
        return dispatchService.getDroneMedicationList(droneId);
    }

    //check available drones
    @GetMapping("/available-drones")
    public List<Drone> getAvailableDrones(){
        return dispatchService.getAvailableDrones();
    }

    //check drone battery level
    @GetMapping("/batter-level/{droneId}")
    public BatteryLevelResponse getBatteryLevel(@PathVariable Integer droneId){
        return dispatchService.getDroneBatteryLevel(droneId);
    }
}
