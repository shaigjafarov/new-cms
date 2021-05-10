package com.guavapay.cms.orderservice.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneratedCardDTO {
    private String cardNumber;
    private String accountNumber;
    private Long orderId;

    public GeneratedCardDTO(String cardNumber, String accountNumber) {
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
    }
}
