package com.guavapay.cms.orderservice.exception;

public class OrderAlreadySubmitted extends RuntimeException {
    public OrderAlreadySubmitted() {
        super();
    }

    public OrderAlreadySubmitted(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrderAlreadySubmitted(final String message) {
        super(message);
    }
}
