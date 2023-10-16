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

    /** 애플리케이션 상태 체크 **/
    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    /** 유저의 주문 생성 **/
    @PostMapping("/{userId}/orders")
    public ResponseEntity<Long> createOrders(
            @PathVariable String userId,
            @RequestBody @Valid CreateOrdersRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.ordersService.createOrders(userId,request));
    }

    /** 유저가 주문한 모든 주문 조회 **/
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<GetOrdersResponseDto>> getOrdersByUserId(
            @PathVariable(name = "userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ordersService.getOrdersByUserId(userId));
    }

    /** 주문 아이디에 해당하는 주문 정보 조회 **/
    @GetMapping("/{orderId}/orders")
    public ResponseEntity<GetOrdersResponseDto> getOrdersByOrderId(
            @PathVariable(name = "orderId") String orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ordersService.getOrdersByOrderId(orderId));
    }

    @DeleteMapping("/{orderId}/orders")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable String orderId
    ) {
        this.ordersService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
