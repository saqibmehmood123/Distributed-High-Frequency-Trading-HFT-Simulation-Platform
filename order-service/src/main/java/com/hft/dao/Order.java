package com.hft.dao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
//import lombok.Data;
import java.time.LocalDateTime;

//@Data
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_symbol_status", columnList = "symbol,status"),
        @Index(name = "idx_side_price", columnList = "side,price")
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String symbol;
    private double price;
    @Positive(message = "Quantity must be positive")
    private int quantity;
    @Pattern(regexp = "BUY|SELL", message = "Side must be 'BUY' or 'SELL'")
    private String side; // "BUY" or "SELL"

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;            // Optimistic locking

    public Order() {
          }

    public Order(Long orderId, String symbol, double price, int quantity, String side, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}