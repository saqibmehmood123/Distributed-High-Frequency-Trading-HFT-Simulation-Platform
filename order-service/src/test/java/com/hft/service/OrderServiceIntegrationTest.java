package com.hft.service;


import com.hft.dao.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

//@Testcontainers
//@SpringBootTest
public class OrderServiceIntegrationTest {

   /* @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private OrderService orderService;

    @Test
    void placeOrder_PersistsToDatabase() {
        Order order = new Order();
        order.setSymbol("AAPL");
        order.setPrice(150.25);
        order.setQuantity(100);
        order.setSide("BUY");
        Order saved = orderService.placeOrder(order);
        assertNotNull("BUY" , saved.getSide());
    }*/
}