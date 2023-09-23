package com.example.service;


import com.example.domain.entity.Catalogs;
import com.example.domain.entity.Orders;
import com.example.domain.entity.Users;
import com.example.domain.repository.CatalogsRepository;
import com.example.domain.repository.OrdersRepository;
import com.example.domain.repository.UsersRepository;
import com.example.dto.CreateOrdersRequestDto;
import com.example.dto.GetOrdersResponseDto;
import com.example.exception.OrderNotFoundException;
import com.example.exception.ProductNotFoundException;
import com.example.exception.UserNotFoundException;
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
    private final UsersRepository usersRepository;
    private final CatalogsRepository catalogsRepository;

    /* 주문 생성 */
    @Transactional
    public Long createOrders(String userId, CreateOrdersRequestDto request) {

        // 회원 정보 확인
        Users users = this.usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("일치하는 회원이 존재하지 않습니다."));

        // 카탈로그 정보 확인
        Catalogs catalogs = this.catalogsRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("일치하는 상품이 존재하지 않습니다."));

        return this.ordersRepository.save(request.toEntity(users, UUID.randomUUID().toString())).getId();
    }

    /* userId 에 해당하는 모든 주문 정보 가져오기 */
    @Transactional(readOnly = true)
    public List<GetOrdersResponseDto> getOrdersByUserId(String userId) {
        Users users = this.usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("일치하는 회원이 존재하지 않습니다."));

        return this.ordersRepository.findAllByUsers(users).stream()
                .map(Orders::toGetOrdersResponseDto)
                .collect(Collectors.toList());
    }

    /* orderId 에 해당하는 주문 정보 가져오기 */
    @Transactional(readOnly = true)
    public GetOrdersResponseDto getOrdersByOrderId(String orderId) {
        return this.ordersRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("일치하는 주문이 존재하지 않습니다."))
                .toGetOrdersResponseDto();


    }
}
