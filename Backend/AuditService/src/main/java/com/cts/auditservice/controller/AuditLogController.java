package com.cts.auditservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auditservice.dto.AuditDto;
import com.cts.auditservice.service.AuditLogService;

/**
 * REST controller for managing audit logs.
 * <p>
 * Provides endpoints to create and retrieve audit records.
 */
@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService service;

    /**
     * Returns all audit logs.
     *
     * @return list of {@link AuditDto}
     */
    @GetMapping
    public List<AuditDto> getAllAuditLog() {
        return service.getAllAuditLogs();
    }

    /**
     * Returns audit logs for a specific car service ID.
     *
     * @param carServiceId the car service ID
     * @return list of {@link AuditDto}
     */
    @GetMapping("/{carServiceId}")
    public List<AuditDto> getAuditLogByCarServiceId(@PathVariable Long carServiceId) {
        return service.findByCarServiceId(carServiceId);
    }

    /**
     * Creates a new audit log entry.
     *
     * @param log the audit log data
     * @return the created {@link AuditDto}
     */
    @PostMapping("/save")
    public AuditDto addAuditLog(@RequestBody AuditDto log) {
        return service.addAuditLog(log);
    }
}
