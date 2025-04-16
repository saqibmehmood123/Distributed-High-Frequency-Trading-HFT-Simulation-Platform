package com.hft.service;

import com.hft.dao.Order;
import com.hft.exceptionhandling.OrderNotFoundException;
import com.hft.repository.OrderRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void cancelOrder_DeletesFromRepository() {
        orderService.cancelOrder(1L);
        verify(orderRepo).deleteById(1L);
    }

    // ---- 1. Basic CRUD Tests ----
    @Test
    void placeOrder_ValidOrder_SavesToRepository() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");
        when(orderRepo.save(order)).thenReturn(order);

        Order result = orderService.placeOrder(order);

        verify(orderRepo).save(order);
        assertEquals("BUY", result.getSide());
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
    @Test
    void placeOrder_InvalidQuantity_ThrowsException() {

        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(-100);
        order.setSide("BUY");
       assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(order));
        verifyNoInteractions(orderRepo);
    }

    // ---- 2. Order Book Tests ----
    @Test
    void getTopOrders_DelegatesToRepository() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");
        Order order2 = new Order();
        order2.setSymbol("AAPL");
        order2.setPrice(150.25);
        order2.setQuantity(100);
        order2.setSide("BUY");
        List<Order> mockOrders = List.of(
              order,
                order
        );
        when(orderRepo.findTopOrders("AAPL", "BUY")).thenReturn(mockOrders);

        List<Order> result = orderService.getTopOrders("AAPL", "BUY");

        assertEquals(2, result.size());
        verify(orderRepo).findTopOrders("AAPL", "BUY");
    }

    // ---- 3. Concurrency Tests ----
    @Test
    void decrementOrderQuantity_UpdatesWithLock() throws OrderNotFoundException {

        Order lockedOrder = new Order();
        lockedOrder.setSymbol("AAPL");
        lockedOrder.setPrice(150.25);
        lockedOrder.setQuantity(100);
        lockedOrder.setSide("BUY");

        lockedOrder.setVersion(1L);
        when(orderRepo.findByIdForUpdate(1L)).thenReturn(Optional.of(lockedOrder));
        when(orderRepo.decrementQuantity(1L, 50, 1L)).thenReturn(1);

        orderService.decrementOrderQuantity(1L, 50);

        verify(orderRepo).decrementQuantity(1L, 50, 1L);
    }

/*
    @Test
    void decrementOrderQuantity_VersionConflict_ThrowsException() {
        when(orderRepo.findByIdForUpdate(1L)).thenReturn(Optional.of(new Order()));
        when(orderRepo.decrementQuantity(any(), anyInt(), anyLong())).thenReturn(0);

        assertThrows(OptimisticLockException.class,
                () -> orderService.decrementOrderQuantity(1L, 50));
    }
*/

    // ---- 4. Bulk Operations ----
    @Test
    void bulkCancelOrders_ExecutesUpdateQuery() {
        when(orderRepo.cancelAllBySymbol("AAPL")).thenReturn(5);

        orderService.bulkCancelOrders("AAPL");

        verify(orderRepo).cancelAllBySymbol("AAPL");
    }

    // ---- 5. Analytics Tests ----
    @Test
    void getTradedVolume_ReturnsSumFromRepository() {
        when(orderRepo.getTradedVolume("AAPL")).thenReturn(5000.0);
        assertEquals(5000.0, orderService.getTradedVolume("AAPL"));
    }



}