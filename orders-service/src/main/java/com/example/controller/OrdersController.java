package com.example.controller;

import com.example.dto.CreateOrdersRequestDto;
import com.example.dto.GetOrdersResponseDto;
import com.example.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/{userId}/orders")
    public ResponseEntity<Long> createOrders(
            @PathVariable String userId,
            @RequestBody @Valid CreateOrdersRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.ordersService.createOrders(userId,request));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<GetOrdersResponseDto>> getOrdersByUserId(
            @PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ordersService.getOrdersByUserId(userId));
    }
}
