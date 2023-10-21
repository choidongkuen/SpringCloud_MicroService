package com.example.kafka.service;

import com.example.domain.entity.Catalogs;
import com.example.domain.repository.CatalogsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final CatalogsRepository catalogsRepository;

    @Transactional
    @KafkaListener(topics = "orders-catalogs-topic")
    public void updateQty(String kafkaMessage) {
        log.info("KafkaMessage = {}", kafkaMessage);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // json string 데이터를 Java Map 으로 Deserialize
            map = objectMapper.readValue(kafkaMessage, new TypeReference<>() {
            });
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        // {"/"productId/" : "CATALOGS-01", "/productName/" : "아이폰 15", "/stock/" : 10 , "/unitPrice/" : 200000 }
        Catalogs catalogs = this.checkRequestInfo(map);

        log.info("Stock {} is minus",map.get("stock"));
        catalogs.minusStock((Integer)map.get("stock"));
    }

    private Catalogs checkRequestInfo(Map<String,Object> map) {
        Catalogs catalogs = this.catalogsRepository.findByProductId((String)map.get("productId"))
                .orElseThrow(() -> new RuntimeException("일치하는 카탈로그 정보가 존재하지 않습니다."));

        if(catalogs.getStock() < (Integer)map.get("stock")) {
            throw new RuntimeException("주문하는 stock 이 재고보다 많습니다.");
        }

        if(catalogs.getUnitPrice() != (Integer)map.get("unitPrice")) {
            throw new RuntimeException("단위 가격이 일치하지 않습니다.");
        }
        return catalogs;
    }
}
