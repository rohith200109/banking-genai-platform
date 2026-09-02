package com.bank.ai.accountservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import com.bank.ai.accountservice.event.TransactionCompletedEvent;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    /**
     * ConsumerFactory for TransactionCompletedEvent
     */
    @Bean
    public ConsumerFactory<String, TransactionCompletedEvent>
    transactionCompletedConsumerFactory() {

        return consumerFactory(
                TransactionCompletedEvent.class
        );
    }

    /**
     * Generic ConsumerFactory creation
     */
    private <T> ConsumerFactory<String, T> consumerFactory(
            Class<T> eventType) {

        Map<String, Object> properties =
                new HashMap<>();

        // Kafka broker
        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        // Consumer group
        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId
        );

        // Start from earliest available message
        properties.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                autoOffsetReset
        );

        // Key deserializer
        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        // JSON value deserializer
        properties.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JacksonJsonDeserializer.class
        );

        /*
         * Tell Jackson which Java class
         * should be created from Kafka JSON.
         */
        properties.put(
                "spring.json.value.default.type",
                eventType.getName()
        );

        /*
         * Allow deserialization of our event class.
         */
        properties.put(
                "spring.json.trusted.packages",
                "com.bank.ai.accountservice.event"
        );

        /*
         * We don't depend on Kafka type headers.
         * The event type is explicitly configured above.
         */
        properties.put(
                "spring.json.use.type.headers",
                false
        );

        return new DefaultKafkaConsumerFactory<>(
                properties
        );
    }

    /**
     * Kafka listener container factory
     */
    @Bean(name = "transactionKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<
            String,
            TransactionCompletedEvent>
    transactionKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<
                String,
                TransactionCompletedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                transactionCompletedConsumerFactory()
        );

        return factory;
    }
}