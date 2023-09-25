package com.example.controller;

import com.example.dto.CreateOrdersRequestDto;
import com.example.dto.GetOrdersResponseDto;
import com.example.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders-service")
@RestController
public class OrdersController {
    private final OrdersService ordersService;
    private final Environment environment;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<Long> createOrders(
            @PathVariable String userId,
            @RequestBody @Valid CreateOrdersRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.ordersService.createOrders(userId,request));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<GetOrdersResponseDto>> getOrdersByUserId(
            @PathVariable(name = "userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ordersService.getOrdersByUserId(userId));
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<GetOrdersResponseDto> getOrdersByOrderId(
            @PathVariable(name = "orderId") String orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ordersService.getOrdersByOrderId(orderId));
    }
}
