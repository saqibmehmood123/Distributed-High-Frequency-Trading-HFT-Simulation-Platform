package com.hft.service;

import com.hft.dao.Order;
import com.hft.dao.OrderStatus;
import com.hft.exceptionhandling.OrderNotFoundException;
import com.hft.repository.OrderRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Transactional
    @CacheEvict(value = "orderBook", key = "{#order.symbol, #order.side}")

    public Order placeOrder(Order order) {
        // Validate quantity
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        // Validate side (optional)
        if (!order.getSide().equals("BUY") && !order.getSide().equals("SELL")) {
            throw new IllegalArgumentException("Side must be 'BUY' or 'SELL'");
        }
        return orderRepo.save(order);
    }
    // Add more methods (e.g., cancelOrder, findOrdersBySymbol)


    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    // Get orders by symbol
    public List<Order> getOrdersBySymbol(String symbol) {
        return orderRepo.findBySymbol(symbol);
    }

    // Cancel order by ID
    @Transactional
    public void cancelOrder(Long orderId) {
        orderRepo.deleteById(orderId);
    }


    // ==== 1. Basic CRUD ====


    // ==== 2. Order Book Operations ====
    public List<Order> getOpenOrdersForSymbol(String symbol) {
        return orderRepo.findBySymbolAndStatus(symbol, OrderStatus.OPEN);
    }
    @Cacheable(value = "orders", key = "#orderId")
    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow();
    }
    @Cacheable(value = "orderBook", key = "{#symbol, #side}")  // Auto-caches
    public List<Order> getTopOrders(String symbol, String side) {
        return orderRepo.findTopOrders(symbol, side);  // Price-time priority
    }

    // ==== 3. Concurrency & Matching ====
    @Transactional
    public Order getOrderForUpdate(Long id) throws OrderNotFoundException {
        return orderRepo.findByIdForUpdate(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public void decrementOrderQuantity(Long id, int delta) throws OrderNotFoundException {
        Order order = getOrderForUpdate(id);  // Pessimistic lock
        int updatedRows = orderRepo.decrementQuantity(id, delta, order.getVersion() );

        if (updatedRows == 0) {
            throw new OptimisticLockException("Order quantity update failed due to version conflict");
        }
    }

    // ==== 4. Bulk Operations ====
    public void bulkCancelOrders(String symbol) {
        orderRepo.cancelAllBySymbol(symbol);
    }

    public List<Order> batchInsertOrders(List<Order> orders) {
        return orderRepo.saveAll(orders);
    }

    // ==== 5. Analytics ====
    public double getTradedVolume(String symbol) {
        return orderRepo.getTradedVolume(symbol);
    }

    public long countOpenOrders(String symbol) {
        return orderRepo.countOpenOrders(symbol);
    }

    // ==== Helper Methods ====
    private void validateOrder(Order order) {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!order.getSide().equals("BUY") && !order.getSide().equals("SELL")) {
            throw new IllegalArgumentException("Side must be BUY or SELL");
        }
    }
}