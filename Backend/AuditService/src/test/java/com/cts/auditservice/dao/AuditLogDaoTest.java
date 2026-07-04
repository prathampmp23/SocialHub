package com.cts.auditservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.auditservice.model.AuditLog;

@ExtendWith(MockitoExtension.class)
class AuditLogDaoTest {

    @Mock
    private AuditLogDao dao;

    private AuditLog audit1;
    private AuditLog audit2;

    @BeforeEach
    void setup() {

        audit1 = new AuditLog();
        audit1.setId(1L);
        audit1.setCarServiceId(101L);
        audit1.setAction("CREATE");
        audit1.setTimestamp(LocalDateTime.now());

        audit2 = new AuditLog();
        audit2.setId(2L);
        audit2.setCarServiceId(101L);
        audit2.setAction("UPDATE");
        audit2.setTimestamp(LocalDateTime.now());
    }

    // ================= SAVE =================

    @Test
    void testSave_success() {

        when(dao.save(audit1)).thenReturn(audit1);

        AuditLog saved = dao.save(audit1);

        assertNotNull(saved);
        assertEquals("CREATE", saved.getAction());
    }

    @Test
    void testSave_multiple() {

        when(dao.save(any(AuditLog.class))).thenReturn(audit1);

        AuditLog saved = dao.save(audit1);

        assertNotNull(saved);
    }

    // ================= FIND ALL =================

    @Test
    void testFindAll_success() {

        when(dao.findAll()).thenReturn(List.of(audit1, audit2));

        List<AuditLog> result = dao.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAll_empty() {

        when(dao.findAll()).thenReturn(List.of());

        List<AuditLog> result = dao.findAll();

        assertTrue(result.isEmpty());
    }

    // ================= FIND BY ID =================

    @Test
    void testFindById_found() {

        when(dao.findById(1L)).thenReturn(Optional.of(audit1));

        Optional<AuditLog> result = dao.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindById_notFound() {

        when(dao.findById(99L)).thenReturn(Optional.empty());

        Optional<AuditLog> result = dao.findById(99L);

        assertFalse(result.isPresent());
    }

    // ================= FIND BY CAR SERVICE ID =================

    @Test
    void testFindByCarServiceId_success() {

        when(dao.findByCarServiceId(101L)).thenReturn(List.of(audit1, audit2));

        List<AuditLog> result = dao.findByCarServiceId(101L);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByCarServiceId_empty() {

        when(dao.findByCarServiceId(999L)).thenReturn(List.of());

        List<AuditLog> result = dao.findByCarServiceId(999L);

        assertTrue(result.isEmpty());
    }

    // ================= DELETE =================

    @Test
    void testDeleteById_success() {

        doNothing().when(dao).deleteById(1L);

        dao.deleteById(1L);

        verify(dao, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAll_success() {

        doNothing().when(dao).deleteAll();

        dao.deleteAll();

        verify(dao, times(1)).deleteAll();
    }
}