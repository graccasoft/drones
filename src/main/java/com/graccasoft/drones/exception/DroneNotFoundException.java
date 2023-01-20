package com.graccasoft.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:45 pm
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends RuntimeException {
    public DroneNotFoundException(String message) {
        super(message);
    }
}
