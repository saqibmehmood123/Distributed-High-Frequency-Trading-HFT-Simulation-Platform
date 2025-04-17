package com.hft;


import org.springframework.boot.autoconfigure.SpringBootApplication;
////import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
@EnableCaching  // <<-- Critical
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


}