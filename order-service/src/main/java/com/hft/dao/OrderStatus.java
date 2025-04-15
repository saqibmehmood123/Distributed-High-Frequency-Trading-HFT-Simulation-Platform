package com.hft.dao;

public enum OrderStatus {
    // === Core States ===
    NEW,                 // Order received but not yet processed
    OPEN,                // Active in the order book
    PARTIALLY_FILLED,    // Partially matched (e.g., 100/200 shares filled)
    FILLED,              // Fully executed
    CANCELLED,           // Explicitly cancelled by user/system
    REJECTED,            // Failed validation (e.g., invalid price)

    // === Advanced HFT States ===
    PENDING_CANCEL,      // Cancel request sent but not confirmed
    EXPIRED,             // Good-Til-Time (GTT) order expired
    ICEBERG_REMAINING,   // For iceberg orders (hidden quantity)
    STOPPED,             // Stop order triggered but not yet a market order
    SUSPENDED            // Temporarily inactive (e.g., market halt)
}