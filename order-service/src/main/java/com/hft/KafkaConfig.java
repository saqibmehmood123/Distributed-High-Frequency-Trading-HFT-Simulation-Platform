package com.hft;

import com.hft.kafka.CacheInvalidationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;

import java.util.Collections;

@Configuration
public class KafkaConfig {

    @Bean
    public RetryTopicConfiguration kafkaRetryConfig(KafkaTemplate<String, CacheInvalidationEvent> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .fixedBackOff(1000)  // 1-second delay
                .maxAttempts(3)       // 3 retries
                .includeTopics(Collections.singletonList("cache-invalidation"))
                .create(template);
    }
}