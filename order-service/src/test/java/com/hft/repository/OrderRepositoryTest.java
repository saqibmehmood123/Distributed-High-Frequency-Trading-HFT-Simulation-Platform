package com.hft.repository;

import com.hft.dao.Order;
import com.hft.dao.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class OrderRepositoryTest  {

    @Autowired
    private OrderRepository orderRepo;

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");

        Order saved = orderRepo.save(order);
        assertNotNull(saved.getOrderId());
    }
    @Test
    public void testSaveOrder1() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");

        Order saved = orderRepo.save(order);
        assertNotNull(saved.getOrderId());
    }



    @Test
    void findTopOrders_ReturnsPriceTimePriority() {
        // Arrange

        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");
        order.setOrderStatus((OrderStatus.OPEN));   // <<-- REQUIRED!

        Order order2 = new Order();
        order2.setSymbol("AAPL");
        order2.setPrice(150.25);
        order2.setQuantity(100);
        order2.setSide("BUY");
        order2.setOrderStatus((OrderStatus.OPEN));  // <<-- REQUIRED!

       List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(order2);
        mockOrders.add(order);
//        when(orderRepo.findTopOrders("AAPL", "BUY")).thenReturn(mockOrders);

        orderRepo.saveAll(mockOrders);
        // Act
        List<Order> result = orderRepo.findTopOrders("AAPL", "BUY");

        // Assert
        assertEquals(2, result.size());
        assertEquals(150.25, result.get(0).getPrice());  // Highest price first

    }


    @Test
    void cancelAllBySymbol_ReturnsAffectedRows() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");
        order.setOrderStatus((OrderStatus.OPEN));   // <<-- REQUIRED!

        Order order2 = new Order();
        order2.setSymbol("AAPL");
        order2.setPrice(150.25);
        order2.setQuantity(100);
        order2.setSide("BUY");
        order2.setOrderStatus((OrderStatus.OPEN));  // <<-- REQUIRED!
        orderRepo.saveAll(List.of(order, order2));

        // Act: Call the REAL method
        int affectedRows = orderRepo.cancelAllBySymbol("AAPL");

        // Assert: Verify database state
        assertEquals(2, affectedRows);  // Only 2 "OPEN" orders cancelled

    /*    List<Order> cancelledOrders = orderRepo.findBy  //setOrderStatus((OrderStatus.OPEN));
        assertEquals(2, cancelledOrders.size());  // Explicit check
    */

    }




/*
    @Test
    void getTradedVolume_ReturnsSum() {
        // Arrange
        when(orderRepo.getTradedVolume("AAPL")).thenReturn(5000.0);

        // Act
        Double volume = orderRepo.getTradedVolume("AAPL");

        // Assert
        assertEquals(5000.0, volume);
        verify(orderRepo).getTradedVolume("AAPL");
    }

*/

}