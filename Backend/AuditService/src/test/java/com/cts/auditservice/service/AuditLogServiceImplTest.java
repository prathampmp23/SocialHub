package com.cts.auditservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.auditservice.dao.AuditLogDao;
import com.cts.auditservice.dto.AuditDto;
import com.cts.auditservice.model.AuditLog;
import com.cts.auditservice.utils.AuditUtils;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceImplTest {

    @Mock
    private AuditLogDao dao;

    @InjectMocks
    private AuditLogServiceImpl service;

    private AuditLog auditEntity;
    private AuditDto auditDto;

    @BeforeEach
    void setup() {

        auditEntity = new AuditLog();
        auditEntity.setCarServiceId(1L);
        auditEntity.setAction("CREATE");
        auditEntity.setTimestamp(LocalDateTime.now());
        auditEntity.setPerformedBy("testUser");
        auditEntity.setDetails("Created successfully");

        auditDto = AuditUtils.entityToDto(auditEntity);
    }

    // ================= GET ALL =================

    @Test
    void testGetAllAuditLogs_success() {

        when(dao.findAll()).thenReturn(List.of(auditEntity));

        List<AuditDto> result = service.getAllAuditLogs();

        assertEquals(1, result.size());
        assertEquals("CREATE", result.get(0).getAction());
    }

    @Test
    void testGetAllAuditLogs_empty() {

        when(dao.findAll()).thenReturn(List.of());

        List<AuditDto> result = service.getAllAuditLogs();

        assertTrue(result.isEmpty());
    }

    // ================= FIND BY CAR SERVICE ID =================

    @Test
    void testFindByCarServiceId_success() {

        when(dao.findByCarServiceId(1L)).thenReturn(List.of(auditEntity));

        List<AuditDto> result = service.findByCarServiceId(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getCarServiceId());
    }

    @Test
    void testFindByCarServiceId_noData() {

        when(dao.findByCarServiceId(99L)).thenReturn(List.of());

        List<AuditDto> result = service.findByCarServiceId(99L);

        assertTrue(result.isEmpty());
    }

    // ================= ADD AUDIT LOG (Kafka Listener) =================

    @Test
    void testAddAuditLog_success() {

        when(dao.save(any(AuditLog.class))).thenReturn(auditEntity);

        AuditDto result = service.addAuditLog(auditDto);

        assertNotNull(result);
        assertEquals("CREATE", result.getAction());

        verify(dao, times(1)).save(any(AuditLog.class));
    }

    @Test
    void testAddAuditLog_verifyFields() {

        when(dao.save(any(AuditLog.class))).thenReturn(auditEntity);

        AuditDto result = service.addAuditLog(auditDto);

        assertEquals(auditDto.getCarServiceId(), result.getCarServiceId());
        assertEquals(auditDto.getPerformedBy(), result.getPerformedBy());
    }
}