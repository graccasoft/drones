package com.graccasoft.drones.errorhandling;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 5:03 pm
 */
public record ErrorDetails(LocalDate timestamp,
                           String message,
                           String endPoint,
                           ArrayList<String> details) {
}
