package com.graccasoft.drones.entity;

import com.graccasoft.drones.enums.DroneModel;
import com.graccasoft.drones.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name="drone_medication",
            joinColumns = @JoinColumn(name = "drone_id"),
            inverseJoinColumns = @JoinColumn(name="medication_id"))
    private List<Medication> loadedMedication;
}
