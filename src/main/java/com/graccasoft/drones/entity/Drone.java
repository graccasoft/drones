package com.graccasoft.drones.entity;

import com.graccasoft.drones.enums.DroneModel;
import com.graccasoft.drones.enums.DroneState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 2:46 pm
 */
@Entity
@Getter
@Setter
public class Drone extends BaseEntity {

    @NotNull
    @Length(min=1, max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Range(min=1, max = 500)
    private BigDecimal weightLimit;

    @Range(min=0, max = 100)
    private BigDecimal percentage;

    @Enumerated(EnumType.STRING)
    private DroneState state;
}
