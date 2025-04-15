package com.hft.repository;


import com.hft.dao.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

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

}