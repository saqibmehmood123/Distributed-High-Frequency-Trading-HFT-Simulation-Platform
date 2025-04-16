package com.hft.repository;

import com.hft.dao.Order;
import com.hft.dao.OrderStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBySymbolAndSide(String symbol, String side);
    List<Order> findBySymbol(String symbol);

    // Custom query: Find BUY orders with quantity > minQuantity
    List<Order> findBySideAndQuantityGreaterThan(String side, int minQuantity);


    // ==== 1. Basic CRUD ====
    @Override
    Optional<Order> findById(Long id);  // Standard fetch

    @Override
    <S extends Order> S save(S entity);  // Upsert

    @Override
    void deleteById(Long id);  // Cancel order


    // ==== 2. High-Performance Querying ====
    // Find by Order ID (UUID, indexed)
    Optional<Order> findByOrderId(Long orderId);

    // Get all open orders for a symbol (for OrderBook)
    List<Order> findBySymbolAndStatus(String symbol, OrderStatus status);

    // Get top 10 BUY/SELL orders (price-time priority)
    @Query("SELECT o FROM Order o WHERE o.symbol = :symbol AND o.side = :side AND o.status = 'OPEN' " +
            "ORDER BY o.price DESC, o.createdAt ASC LIMIT 10")
    List<Order> findTopOrders(@Param("symbol") String symbol, @Param("side") String side);


    // ==== 3. Concurrency & Locking ====
    // Pessimistic lock for order matching
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findByIdForUpdate(@Param("id") Long id);

    // Optimistic locking (versioned updates)
    @Query("UPDATE Order o SET o.quantity = o.quantity - :delta WHERE o.id = :id AND o.version = :version")
    int decrementQuantity(@Param("id") Long id, @Param("delta") int delta, @Param("version") Long version);


    // ==== 4. Bulk Operations ====
    // Batch insert (for market data simulation)
    <S extends Order> List<S> saveAll(Iterable<S> orders);

    // Bulk cancel by symbol (e.g., market close)
    @Modifying
    @Query("UPDATE Order o SET o.status = 'CANCELLED' WHERE o.symbol = :symbol")
    int cancelAllBySymbol(@Param("symbol") String symbol);

    @Query("SELECT SUM(o.quantity) FROM Order o WHERE o.symbol = :symbol AND o.status = 'FILLED'")
    Double getTradedVolume(@Param("symbol") String symbol) ;


    // ==== 5. Analytics & Monitoring ====
    // Count open orders by symbol
    @Query("SELECT COUNT(o) FROM Order o WHERE o.symbol = :symbol AND o.status = 'OPEN'")
    long countOpenOrders(@Param("symbol") String symbol);

    // Get total traded volume (for dashboard)



}