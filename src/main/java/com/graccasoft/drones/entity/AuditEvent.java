package com.graccasoft.drones.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 8:27 pm
 */
@Entity
@Getter
@Setter
public class AuditEvent extends BaseEntity {
    private Instant timestamp;
    private String type;
    private String data;
}
