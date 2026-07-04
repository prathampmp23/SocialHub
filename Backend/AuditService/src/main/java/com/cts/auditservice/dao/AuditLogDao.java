package com.cts.auditservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.auditservice.model.AuditLog;

/**
 * Repository interface for {@link AuditLog}.
 * <p>
 * Provides database access operations for audit log records.
 */
@Repository
public interface AuditLogDao extends JpaRepository<AuditLog, Long> {

    /**
     * Returns audit logs for the given car service ID.
     *
     * @param id the car service ID
     * @return list of {@link AuditLog}
     */
    List<AuditLog> findByCarServiceId(Long id);
}