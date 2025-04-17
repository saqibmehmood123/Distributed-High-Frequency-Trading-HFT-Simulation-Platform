package com.hft.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private KafkaTemplate<String, CacheInvalidationEvent> kafkaTemplate;

    @CacheEvict(cacheNames = "orderBook", key = "{#symbol + ':' + #side}")
    public void evictLocalCache(String symbol, String side) {
        // Also publish event to Kafka
        kafkaTemplate.send("cache-invalidation",
                new CacheInvalidationEvent("orderBook", symbol + ":" + side)  );
    }
}