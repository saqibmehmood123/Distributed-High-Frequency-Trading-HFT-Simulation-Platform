package com.hft.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CacheListener {

    @Autowired
    private CacheManager ehCacheManager;

    @KafkaListener(topics = "cache-invalidation")
    public void handleCacheInvalidation(CacheInvalidationEvent event) {
        Cache cache = ehCacheManager.getCache(event.getCacheName());
        if (cache != null) {
            cache.evict(event.getKey()); // Evict in local EHCache
        }
    }
}