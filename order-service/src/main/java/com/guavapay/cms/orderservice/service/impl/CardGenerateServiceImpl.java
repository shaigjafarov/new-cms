package com.guavapay.cms.orderservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.cms.orderservice.config.RabbitConfiguration;
import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;
import com.guavapay.cms.orderservice.service.CardGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardGenerateServiceImpl implements CardGenerateService {
    private final RabbitTemplate template;

    @Override
    public GeneratedCardDTO randomGenerate() {
        GeneratedCardDTO generatedCardDTO = new GeneratedCardDTO();
        generatedCardDTO.setCardNumber(generateRandomPAN());
        generatedCardDTO.setAccountNumber(generateRandomAccountNumber(10));
        return generatedCardDTO;
    }

    @Override
    public void cardGenerateProcessing(Long orderId) {
        try {
            Thread.sleep(10000);
            GeneratedCardDTO generatedCardDTO = randomGenerate();
            generatedCardDTO.setOrderId(orderId);
            var generatedCardDTOJson = new ObjectMapper().writeValueAsString(generatedCardDTO);
            template.convertAndSend(RabbitConfiguration.queueResultOrder, generatedCardDTOJson);
        } catch (Exception e) {
            log.error("card generated time exception", e);
        }


    }

    private String generateRandomAccountNumber(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
             0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    private String generateRandomPAN() {
        var smallest = 1000_0000_0000_0000L;
        var biggest = 9999_9999_9999_9999L;
        var randomPan = ThreadLocalRandom.current().nextLong(smallest, biggest + 1);
        return Long.toString(randomPan);
    }

}
