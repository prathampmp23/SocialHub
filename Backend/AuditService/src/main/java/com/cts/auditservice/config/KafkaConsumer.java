package com.cts.auditservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.cts.auditservice.dto.AuditDto;

/**
 * Configuration class for Kafka consumer setup.
 * <p>
 * Defines the consumer factory and listener container factory
 * to consume {@code AuditDto} messages from Kafka topics.
 */
@Configuration
public class KafkaConsumer {

    /**
     * Creates Kafka {@link ConsumerFactory}.
     * <p>
     * Configures deserialization for {@code AuditDto} messages
     * and sets consumer properties such as bootstrap server
     * and group ID.
     *
     * @return the configured {@link ConsumerFactory}
     */
    @Bean
    public ConsumerFactory<String, AuditDto> consumerFactory() {

        JsonDeserializer<AuditDto> deserializer = new JsonDeserializer<>(AuditDto.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);
        deserializer.setRemoveTypeHeaders(true);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "auditlogs-group");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    /**
     * Creates Kafka listener container factory.
     *
     * @return the {@link ConcurrentKafkaListenerContainerFactory}
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AuditDto>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AuditDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
