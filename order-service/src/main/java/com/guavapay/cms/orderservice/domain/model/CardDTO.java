package com.guavapay.cms.orderservice.domain.model;

import com.guavapay.cms.orderservice.domain.enums.CardPeriod;
import com.guavapay.cms.orderservice.domain.enums.CartType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private CartType cartType;
    private CardPeriod cardPeriod;
    private String cardHolderName;
    private boolean urgent;
    private String codeWord;

}
