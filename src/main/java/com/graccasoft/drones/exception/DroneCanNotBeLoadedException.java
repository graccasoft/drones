package com.graccasoft.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:57 pm
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DroneCanNotBeLoadedException extends RuntimeException {
    public DroneCanNotBeLoadedException(String message) {
        super(message);
    }
}
