package com.graccasoft.drones.repository;

import com.graccasoft.drones.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 8:29 pm
 */
public interface AuditEventRepository extends JpaRepository<AuditEvent, Integer> {
}
