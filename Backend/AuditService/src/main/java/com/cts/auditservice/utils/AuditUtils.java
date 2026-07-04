package com.cts.auditservice.utils;


import java.util.ArrayList;
import java.util.List;

import com.cts.auditservice.dto.AuditDto;
import com.cts.auditservice.model.AuditLog;

/**
 * Utility class for converting between {@link AuditLog}
 * and {@link AuditDto}.
 * <p>
 * Provides static helper methods to map data between entity
 * and DTO objects.
 */
public class AuditUtils {

    /**
     * Converts a {@link AuditDto} to {@link AuditLog}.
     *
     * @param dto the data transfer object
     * @return the mapped entity
     */
    public static AuditLog dtoToEntity(AuditDto dto) {
        AuditLog entity = new AuditLog();
        entity.setCarServiceId(dto.getCarServiceId());
        entity.setAction(dto.getAction());
        entity.setTimestamp(dto.getTimestamp());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setDetails(dto.getDetails());
        return entity;
    }

    /**
     * Converts a {@link AuditLog} to {@link AuditDto}.
     *
     * @param entity the entity object
     * @return the mapped DTO
     */
    public static AuditDto entityToDto(AuditLog entity) {
        AuditDto dto = new AuditDto();
        dto.setCarServiceId(entity.getCarServiceId());
        dto.setAction(entity.getAction());
        dto.setTimestamp(entity.getTimestamp());
        dto.setPerformedBy(entity.getPerformedBy());
        dto.setDetails(entity.getDetails());
        return dto;
    }

    /**
     * Converts a list of {@link AuditDto} to a list of {@link AuditLog}.
     *
     * @param dtoList list of DTO objects
     * @return list of mapped entities
     */
    public static List<AuditLog> dtoToEntityList(List<AuditDto> dtoList) {
        List<AuditLog> entityList = new ArrayList<>();
        dtoList.forEach(dto -> entityList.add(dtoToEntity(dto)));
        return entityList;
    }

    /**
     * Converts a list of {@link AuditLog} to a list of {@link AuditDto}.
     *
     * @param entityList list of entity objects
     * @return list of mapped DTOs
     */
    public static List<AuditDto> entityToDtoList(List<AuditLog> entityList) {
        List<AuditDto> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(entityToDto(entity)));
        return dtoList;
    }
}
