package com.graccasoft.drones.controller;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.dto.RegisterDroneRequest;
import com.graccasoft.drones.dto.StandardResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import com.graccasoft.drones.service.DispatchService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 3:13 pm
 */
@RestController
@RequestMapping("/api/v1/dispatch")
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @Operation(summary = "Register a new drone", description = "POST a drone JSON object to this endpoint to register it to the fleet.")
    @PostMapping("register")
    public Drone registerDrone(@Valid @RequestBody RegisterDroneRequest drone){
        return dispatchService.saveDrone(drone);
    }

    @Operation(summary = "Load medication to a drone", description = "droneId is the ID of the drone you want to load. The ID must be valid and of an Idle drone.")
    @PostMapping("load/{droneId}")
    public StandardResponse loadDrone(@Valid @RequestBody List<Medication> medicationItems, @PathVariable Integer droneId){
        dispatchService.loadDrone(droneId, medicationItems);
        return new StandardResponse(200,"Drone loaded");
    }

    @Operation(summary = "View medication loaded to a specific drone")
    @GetMapping("loaded/{droneId}")
    public List<Medication> getLoadedMedication(@PathVariable Integer droneId){
        return dispatchService.getDroneMedicationList(droneId);
    }

    @Operation(summary = "Fetch all available drones")
    @GetMapping("/available-drones")
    public List<Drone> getAvailableDrones(){
        return dispatchService.getAvailableDrones();
    }

    @Operation(summary = "View the battery level for a specific drone")
    @GetMapping("/batter-level/{droneId}")
    public BatteryLevelResponse getBatteryLevel(@PathVariable Integer droneId){
        return dispatchService.getDroneBatteryLevel(droneId);
    }
}
