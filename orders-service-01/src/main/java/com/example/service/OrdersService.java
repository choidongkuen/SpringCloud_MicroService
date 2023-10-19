package com.example.service;


import com.example.domain.entity.Orders;
import com.example.domain.repository.OrdersRepository;
import com.example.dto.CreateOrdersRequestDto;
import com.example.dto.GetOrdersResponseDto;
import com.example.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;

    /* 주문 생성 */
    @Transactional
    public Long createOrders(String userId, CreateOrdersRequestDto request) {
        return this.ordersRepository.save(request.toEntity(UUID.randomUUID().toString(), userId)).getId();
    }

    /* userId 에 해당하는 모든 주문 정보 가져오기 */
    @Transactional(readOnly = true)
    public List<GetOrdersResponseDto> getOrdersByUserId(String userId) {
        return this.ordersRepository.findAllByUserId(userId).stream()
                .map(Orders::toGetOrdersResponseDto)
                .collect(Collectors.toList());
    }

    /* orderId 에 해당하는 주문 정보 가져오기 */
    @Transactional(readOnly = true)
    public GetOrdersResponseDto getOrdersByOrderId(String orderId) {
        return this.getOrders(orderId)
                .toGetOrdersResponseDto();
    }

    /* orderId 에 해당하는 주문 정보 삭제하기 */
    @Transactional
    public void deleteOrder(String orderId) {
        Orders orders = this.getOrders(orderId);
        this.ordersRepository.delete(orders);
    }

    private Orders getOrders(String orderId) {
        return this.ordersRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("일치하는 주문이 존재하지 않습니다."));
    }
}
