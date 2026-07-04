package com.cts.auditservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_service_audit_logs")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "car_service_id", nullable = false)
	private Long carServiceId;

	@Column(name = "action", nullable = false, length = 50)
	private String action;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	@Column(name = "performed_by", nullable = false, length = 100)
	private String performedBy;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCarServiceId() {
		return carServiceId;
	}

	public void setCarServiceId(Long carServiceId) {
		this.carServiceId = carServiceId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getPerformedBy() {
		return performedBy;
	}

	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public AuditLog() {
	}

	public AuditLog(Long carServiceId, String action, LocalDateTime timestamp, String performedBy, String details) {
		this.carServiceId = carServiceId;
		this.action = action;
		this.timestamp = timestamp;
		this.performedBy = performedBy;
		this.details = details;
	}

	@Override
	public String toString() {
		return "AuditLog [id=" + id + ", carServiceId=" + carServiceId + ", action=" + action + ", timestamp="
				+ timestamp + ", performedBy=" + performedBy + ", details=" + details + "]";
	}

}