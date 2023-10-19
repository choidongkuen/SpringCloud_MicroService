package com.example.domain.repository;

import com.example.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Optional<Orders> findByOrderId(String orderId);
    List<Orders> findAllByUserId(String userId);

}
