package com.graccasoft.drones.dto;

import com.graccasoft.drones.enums.DroneModel;
import com.graccasoft.drones.enums.DroneState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * @author graciousmashasha
 * @created 23/01/2023 - 9:18 am
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDroneRequest{
        @NotNull
        @Length(min=1, max = 100)
        private String serialNumber;

        @NotNull
        private DroneModel model;

        @Range(min=1, max = 500)
        private BigDecimal weightLimit;

        @Range(min=0, max = 100)
        private BigDecimal percentage;

        @NotNull
        private DroneState state;
}
