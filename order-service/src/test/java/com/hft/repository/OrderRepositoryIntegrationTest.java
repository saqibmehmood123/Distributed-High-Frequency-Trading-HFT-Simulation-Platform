package com.hft.repository;


import com.hft.dao.Order;
import com.hft.dao.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Testcontainers

public class OrderRepositoryIntegrationTest {

/*    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private OrderRepository orderRepo;

    @Test
    void findTopOrders_ReturnsCorrectOrders() {
        // Insert test data
        orderRepo.save(new Order("AAPL", 100, 150.25, "BUY"));
        orderRepo.save(new Order("AAPL", 200, 150.10, "BUY"));
        orderRepo.save(new Order("GOOGL", 50, 2800.00, "BUY"));  // Should be ignored

        // Act
        List<Order> result = orderRepo.findTopOrders("AAPL", "BUY");

        // Assert
        assertEquals(2, result.size());
        assertEquals(150.25, result.get(0).getPrice());  // Price DESC
    }

    @Test
    void cancelAllBySymbol_UpdatesStatus() {
        // Insert test data
        orderRepo.save(new Order("AAPL", 100, 150.25, "BUY"));
        orderRepo.save(new Order("AAPL", 200, 150.10, "BUY"));

        // Act
        int affectedRows = orderRepo.cancelAllBySymbol("AAPL");

        // Assert
        assertEquals(2, affectedRows);

        assertTrue(orderRepo.findBySymbolAndStatus("AAPL", OrderStatus.CANCELLED ).size() > 0  );
    }

    @Test
    void getTradedVolume_CalculatesCorrectSum() {
        // Insert test data
        Order filledOrder1 = new Order("AAPL", 100, 150.25, "BUY");
        filledOrder1.setOrderStatus(OrderStatus.FILLED);
        orderRepo.save(filledOrder1);

        Order filledOrder2 = new Order("AAPL", 200, 150.10, "BUY");
        filledOrder2.setOrderStatus(OrderStatus.FILLED);
        orderRepo.save(filledOrder2);

        // Act
        Double volume = orderRepo.getTradedVolume("AAPL");

        // Assert
        assertEquals(300.0, volume);  // 100 + 200
    }*/
}