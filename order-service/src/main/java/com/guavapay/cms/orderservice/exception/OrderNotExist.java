package com.guavapay.cms.orderservice.exception;

public class OrderNotExist extends RuntimeException {

    public OrderNotExist() {
        super();
    }

    public OrderNotExist(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrderNotExist(final String message) {
        super(message);
    }


}
