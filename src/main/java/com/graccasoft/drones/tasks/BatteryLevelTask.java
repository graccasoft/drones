package com.graccasoft.drones.tasks;

import com.graccasoft.drones.entity.AuditEvent;
import com.graccasoft.drones.repository.AuditEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author graciousmashasha
 * @created 20/01/2023 - 8:23 pm
 */
@Component
public class BatteryLevelTask {

    private final AuditEventRepository auditEventRepository;

    public BatteryLevelTask(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    //for demonstration purposes, check every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void checkBatteryLevels() {

        //todo to consume some remote rest or such and get battery status
        //dummy response
        Map<String, BigDecimal> remoteServiceResponse = new HashMap<>();
        remoteServiceResponse.put("DRN-001", new BigDecimal(30));
        remoteServiceResponse.put("DRN-002", new BigDecimal(29));
        remoteServiceResponse.put("DRN-003", new BigDecimal(31));

        //log events
        for(String key: remoteServiceResponse.keySet()){
            AuditEvent auditEvent = new AuditEvent();
            auditEvent.setType("BATTERY_LEVEL_CHECK");
            auditEvent.setTimestamp(Instant.now());
            auditEvent.setData( key + ":" + remoteServiceResponse.get(key).toString() );

            auditEventRepository.save(auditEvent);
        }

    }
}
