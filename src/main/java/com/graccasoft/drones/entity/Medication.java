package com.graccasoft.drones.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 2:55 pm
 */
@Entity
@Getter
@Setter
public class Medication extends BaseEntity {

    @Length(min=3, max = 100)
    @Pattern(regexp = "[A-Za-z0-9\\-\\_]+")
    private String name;
    private BigDecimal weight;

    @Pattern(regexp = "[A-Za-z0-9\\-\\_]+")
    private String code;
    private String imageUrl;
}
