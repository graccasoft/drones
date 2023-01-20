package com.graccasoft.drones.dto;

import java.math.BigDecimal;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 3:25 pm
 */
public record BatteryLevelResponse(String message, BigDecimal level) {
}
