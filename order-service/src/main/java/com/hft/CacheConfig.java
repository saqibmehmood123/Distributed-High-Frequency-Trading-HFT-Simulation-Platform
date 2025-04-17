package com.hft;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.cache.CacheManager;
import javax.cache.Caching;
import com.hft.dao.Order; // Ensure this import exists

import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager ehcacheCacheManager() {
        // 1. Define config with Object as value type
        CacheConfiguration<String, Object> config =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class,
                        Object.class,  // Accepts any value (including List<Order>)
                        ResourcePoolsBuilder.heap(1000)
                ).build();

        // 2. Convert to JCache config
        javax.cache.configuration.Configuration<String, Object> jcacheConfig =
                Eh107Configuration.fromEhcacheCacheConfiguration(config);

        // 3. Create cache
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        cacheManager.createCache("orderBook", jcacheConfig);
        return cacheManager;
    }
}