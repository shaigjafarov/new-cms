package com.guavapay.cms.orderservice.service;

import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;

public interface CardGenerateService {

    GeneratedCardDTO randomGenerate();

    void cardGenerateProcessing(Long orderId);
}
