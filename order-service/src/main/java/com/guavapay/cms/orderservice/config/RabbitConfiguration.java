package com.guavapay.cms.orderservice.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;
import com.guavapay.cms.orderservice.service.CardGenerateService;
import com.guavapay.cms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RabbitConfiguration {
    private final CardGenerateService cardGenerateService;
    private final OrderService orderService;
    public static final String queueSubmitOrder = "submitCardOrder";
    public static final String queueResultOrder = "resultOrder";


    @Bean
    Queue queueSubmitOrder() {
        return new Queue(queueSubmitOrder, true);
    }

    @Bean
    Queue queueResultOrder() {

        return new Queue(queueResultOrder, true);
    }


    @RabbitListener(queues = queueResultOrder)
    public void listenQueue(String generatedCardDTOJson) {
        try {
            log.info("received order result " + generatedCardDTOJson);
            ObjectMapper objectMapper = new ObjectMapper();
            GeneratedCardDTO generatedCardDTO = objectMapper.readValue(generatedCardDTOJson, GeneratedCardDTO.class);
            orderService.updateOrderByGeneratedCardService(generatedCardDTO);
        } catch (Exception e) {
            log.error("error accepting order result",e);
        }
    }

    @RabbitListener(queues = queueSubmitOrder)
    public void listenQueueSubmitOrder(Long orderId) {
        try {
            log.info("received order id {}" , orderId);
            cardGenerateService.cardGenerateProcessing(orderId);
        } catch (Exception e) {
            log.error("error accepting order ",e);
        }
    }


}