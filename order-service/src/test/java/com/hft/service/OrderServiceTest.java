package com.hft.service;

import com.hft.dao.Order;
import com.hft.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepo;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrder_ValidOrder_ReturnsSavedOrder() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");

        when(orderRepo.save(any(Order.class))).thenReturn(order);

        Order saved = orderService.placeOrder(order);
        assertEquals("AAPL", saved.getSymbol());
    }

    @Test
    void placeOrder_InvalidQuantity_ThrowsException() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(-1);
        order.setQuantity(100);
        order.setSide("INVALID_SIDE");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder(order);
        });

    }
    @Test
    void placeOrder_ValidOrder_SavesToRepository() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(45);
        order.setQuantity(100);
        order.setSide("BUY");


        when(orderRepo.save(any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.placeOrder(order);
        verify(orderRepo).save(order);
        assertEquals("BUY", savedOrder.getSide());
    }

    @Test
    void cancelOrder_DeletesFromRepository() {
        orderService.cancelOrder(1L);
        verify(orderRepo).deleteById(1L);
    }





}