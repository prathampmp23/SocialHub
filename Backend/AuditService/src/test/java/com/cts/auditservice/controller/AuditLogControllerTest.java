package com.cts.auditservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.auditservice.dto.AuditDto;
import com.cts.auditservice.service.AuditLogService;
import com.cts.auditservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuditLogController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuditLogService service;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    // Common DTO
    private AuditDto getDto() {
        AuditDto dto = new AuditDto();
        dto.setCarServiceId(1L);
        dto.setAction("CREATE");
        dto.setTimestamp(LocalDateTime.now());
        dto.setPerformedBy("testUser");
        dto.setDetails("Test log");
        return dto;
    }

    // ================= GET ALL =================

    @Test
    void testGetAllAuditLogs_success() throws Exception {

        when(service.getAllAuditLogs()).thenReturn(List.of(getDto()));

        mockMvc.perform(get("/api/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetAllAuditLogs_empty() throws Exception {

        when(service.getAllAuditLogs()).thenReturn(List.of());

        mockMvc.perform(get("/api/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    // ================= GET BY ID =================

    @Test
    void testGetByCarServiceId_success() throws Exception {

        when(service.findByCarServiceId(1L)).thenReturn(List.of(getDto()));

        mockMvc.perform(get("/api/audit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].action").value("CREATE"));
    }

    @Test
    void testGetByCarServiceId_noData() throws Exception {

        when(service.findByCarServiceId(99L)).thenReturn(List.of());

        mockMvc.perform(get("/api/audit/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    // ================= ADD =================

    @Test
    void testAddAuditLog_success() throws Exception {

        when(service.addAuditLog(org.mockito.ArgumentMatchers.any()))
                .thenReturn(getDto());

        mockMvc.perform(post("/api/audit/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.action").value("CREATE"));
    }

    @Test
    void testAddAuditLog_differentInput() throws Exception {

        AuditDto dto = getDto();
        dto.setAction("UPDATE");

        when(service.addAuditLog(org.mockito.ArgumentMatchers.any()))
                .thenReturn(dto);

        mockMvc.perform(post("/api/audit/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.action").value("UPDATE"));
    }
}