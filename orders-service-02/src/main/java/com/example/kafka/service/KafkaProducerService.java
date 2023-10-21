package com.example.kafka.service;


import com.example.dto.CreateOrdersRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessageToKafka(String topic, CreateOrdersRequestDto request) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(request);
        }catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        // kafka broker topic 전송
        this.kafkaTemplate.send(topic,jsonString);
        log.info("Kafka Producer send data from the orders-service : " + request);
    }
}
