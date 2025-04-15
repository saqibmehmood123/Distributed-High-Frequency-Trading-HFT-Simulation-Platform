package com.hft.service;

import com.hft.dao.Order;
import com.hft.repository.OrderRepository;
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


}