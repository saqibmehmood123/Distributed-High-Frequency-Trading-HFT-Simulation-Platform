package com.hft.service;

import com.hft.dao.Order;
import com.hft.kafka.CacheService;
import com.hft.repository.OrderRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderBookService {
    private final OrderRepository orderRepo;
    private final CacheService cacheService;

    public OrderBookService(OrderRepository orderRepo, CacheService cacheService) {
        this.orderRepo = orderRepo;
        this.cacheService = cacheService;
    }
    // 1. Local Cache (EHCache)
    @Cacheable(cacheNames = "orderBook", cacheManager = "ehCacheManager")
    public List<Order> getTopOrders(String symbol, String side) {
        return orderRepo.findTopOrders(symbol, side);
    }

    // 2. Distributed Cache (Redis)
    @Cacheable(cacheNames = "tradedVolume", cacheManager = "redisCacheManager")
    public Double getTradedVolume(String symbol) {
        return orderRepo.getTradedVolume(symbol);
    }

    // 3. Invalidate Both Caches on Order Placement
    @Transactional
    public void placeOrder(Order order) {
        orderRepo.save(order);
        cacheService.evictLocalCache(order.getSymbol(), order.getSide()); // Triggers Kafka event
    }
}