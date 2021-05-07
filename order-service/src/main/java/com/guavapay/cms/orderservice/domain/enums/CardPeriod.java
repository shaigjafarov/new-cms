package com.guavapay.cms.orderservice.domain.enums;

public enum CardPeriod {

    ONE_YEAR(12),
    TWO_YEAR(24),
    THREE_YEAR(36);

    private final int value;

    CardPeriod(int value) {
        this.value = value;
    }

}
