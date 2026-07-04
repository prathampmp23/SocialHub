package com.cts.auditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Entry point for the Audit Service application.
 * <p>
 * Starts the Spring Boot application and enables Kafka
 * for consuming audit events and service discovery using Eureka.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class AuditServiceApplication {

    /**
     * Starts the Audit Service application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AuditServiceApplication.class, args);
    }
}