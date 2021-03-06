package com.guavapay.cms.orderservice.domain.entity;

import com.guavapay.cms.orderservice.domain.enums.CardPeriod;
import com.guavapay.cms.orderservice.domain.enums.CardType;
import com.guavapay.cms.orderservice.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Where(clause = "status <>'DELETED'")
@Table(name = "order_card")
public class Order extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private CardPeriod cardPeriod;

    private String cardHolderName;

    private boolean urgent;

    private String codeWord;

    private Long userId;

    private String cardNumber;

    private String accountNumber;

}
