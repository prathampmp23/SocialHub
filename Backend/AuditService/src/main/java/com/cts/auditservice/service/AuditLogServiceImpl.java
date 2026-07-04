package com.cts.auditservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.auditservice.dao.AuditLogDao;
import com.cts.auditservice.dto.AuditDto;
import com.cts.auditservice.model.AuditLog;
import com.cts.auditservice.utils.AuditUtils;

/**
 * Implementation of {@link AuditLogService}.
 * <p>
 * Provides logic for processing audit log events, including
 * consuming messages from Kafka and persisting them.
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogDao dao;

    /**
     * Returns all audit logs.
     *
     * @return list of {@link AuditDto}
     */
    @Override
    public List<AuditDto> getAllAuditLogs() {
        return AuditUtils.entityToDtoList(dao.findAll());
    }

    /**
     * Returns audit logs for the given car service ID.
     *
     * @param id the car service ID
     * @return list of {@link AuditDto}
     */
    @Override
    public List<AuditDto> findByCarServiceId(Long id) {
        return AuditUtils.entityToDtoList(dao.findByCarServiceId(id));
    }

    /**
     * Consumes audit log message from Kafka and stores it in the database.
     *
     * @param log the audit log data
     * @return the saved {@link AuditDto}
     */
    @KafkaListener(
            topics = "auditlog-topic",
            groupId = "auditlogs-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Override
    public AuditDto addAuditLog(AuditDto log) {

        AuditLog auditLog = AuditUtils.dtoToEntity(log);

        AuditLog saved = dao.save(auditLog);

        return AuditUtils.entityToDto(saved);
    }
}