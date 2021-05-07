package com.guavapay.cms.orderservice.domain.entity;

import com.guavapay.cms.orderservice.domain.enums.CardPeriod;
import com.guavapay.cms.orderservice.domain.enums.CartType;
import com.guavapay.cms.orderservice.domain.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_card",schema = "cms")
public class Order extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private CartType cartType;

    @Enumerated(EnumType.STRING)
    private CardPeriod cardPeriod;

    private String cardHolderName;

    private boolean urgent;

    private String codeWord;

    private Long userId;

}
