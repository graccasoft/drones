package com.graccasoft.drones.repository;

import com.graccasoft.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 4:21 pm
 */
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
}
