package com.guavapay.cms.orderservice.domain.model;

import lombok.Data;

@Data
public class GeneratedCardDTO {
    private String cardNumber;
    private String accountNumber;
    private Long orderId;
}
