package com.cts.auditservice.service;

import java.util.List;

import com.cts.auditservice.dto.AuditDto;

/**
 * Service interface for managing audit log operations.
 * <p>
 * Defines methods for retrieving and storing audit log records.
 */
public interface AuditLogService {

    /**
     * Returns all audit logs.
     *
     * @return list of {@link AuditDto}
     */
    List<AuditDto> getAllAuditLogs();

    /**
     * Returns audit logs for the given car service ID.
     *
     * @param id the car service ID
     * @return list of {@link AuditDto}
     */
    List<AuditDto> findByCarServiceId(Long id);

    /**
     * Creates a new audit log entry.
     *
     * @param log the audit log data
     * @return the created {@link AuditDto}
     */
    AuditDto addAuditLog(AuditDto log);
}