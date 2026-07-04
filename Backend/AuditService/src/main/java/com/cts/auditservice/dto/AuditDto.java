package com.cts.auditservice.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for audit log details.
 * <p>
 * Used to transfer audit information between services and
 * represent audit events received from Kafka.
 */
public class AuditDto {

    /**
     * Identifier of the related car service.
     */
    private Long carServiceId;

    /**
     * Action performed on the resource.
     */
    private String action;

    /**
     * Time at which the action occurred.
     */
    private LocalDateTime timestamp;

    /**
     * User who performed the action.
     */
    private String performedBy;

    /**
     * Additional details about the action.
     */
    private String details;

    public Long getCarServiceId() {
        return carServiceId;
    }

    /**
     * Sets car service ID.
     *
     * @param carServiceId the car service ID
     */
    public void setCarServiceId(Long carServiceId) {
        this.carServiceId = carServiceId;
    }

    public String getAction() {
        return action;
    }

    /**
     * Sets action type.
     *
     * @param action action performed
     */
    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp event time
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    /**
     * Sets user who performed the action.
     *
     * @param performedBy username
     */
    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getDetails() {
        return details;
    }

    /**
     * Sets additional details.
     *
     * @param details description of the action
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Default constructor.
     */
    public AuditDto() {
    }

    /**
     * Creates a new AuditDto.
     *
     * @param carServiceId car service ID
     * @param action action performed
     * @param timestamp timestamp
     * @param performedBy user
     * @param details additional details
     */
    public AuditDto(Long carServiceId, String action,
                    LocalDateTime timestamp,
                    String performedBy, String details) {
        this.carServiceId = carServiceId;
        this.action = action;
        this.timestamp = timestamp;
        this.performedBy = performedBy;
        this.details = details;
    }

    @Override
    public String toString() {
        return "AuditLog [carServiceId=" + carServiceId +
                ", action=" + action +
                ", timestamp=" + timestamp +
                ", performedBy=" + performedBy +
                ", details=" + details + "]";
    }
}
