package com.example.client;

import com.example.dto.GetOrdersResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


// orders-service 의 getOrdersByUserId 을 호출하는 client 마냥

@FeignClient(name = "orders-service")
public interface OrderServiceClient {

    // 직접 해당 http 요청 호출
    @GetMapping("/users/{userId}/orderss")
    List<GetOrdersResponseDto> getOrdersByUserId(@PathVariable String userId);
}

