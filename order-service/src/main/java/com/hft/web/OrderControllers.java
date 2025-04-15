package com.hft.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllers {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, HFT World!";
    }
}
