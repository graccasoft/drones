package com.graccasoft.drones.controller;

import com.graccasoft.drones.dto.BatteryLevelResponse;
import com.graccasoft.drones.dto.StandardResponse;
import com.graccasoft.drones.entity.Drone;
import com.graccasoft.drones.entity.Medication;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 3:13 pm
 */
@Controller
@RequestMapping("/api/v1/dispatch")
public class DispatchController {

    //register drone endpoint

    @PostMapping("register")
    public Drone registerDrone(@Valid @RequestBody Drone drone){
        return null;
    }

    @PostMapping("load/{droneId}")
    public StandardResponse loadDrone(@Valid @RequestBody List<Medication> medicationItems, @PathVariable Integer droneId){
        return new StandardResponse(200,"Drone loaded");
    }

    @GetMapping("loaded/{droneId}")
    public List<Medication> getLoadedMedication(@PathVariable Integer droneId){
        return null;
    }

    @GetMapping("/available-drones")
    public List<Drone> getAvailableDrones(){
        return null;
    }

    @GetMapping("/batter-level/{droneId}")
    public BatteryLevelResponse getBatteryLevel(@PathVariable Integer droneId){
        return new BatteryLevelResponse("Battery Level", BigDecimal.ZERO);
    }
}
