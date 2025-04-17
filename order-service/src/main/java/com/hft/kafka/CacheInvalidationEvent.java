package com.hft.kafka;

public class CacheInvalidationEvent {
    private String cacheName;
    private String key;
    // Getters/setters


    public CacheInvalidationEvent(String cacheName, String key) {
        this.cacheName = cacheName;
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}