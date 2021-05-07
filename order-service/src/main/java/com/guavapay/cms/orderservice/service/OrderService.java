package com.guavapay.cms.orderservice.service;

import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findOrderDTOListByUserId(Long userId);
    CardDTO findCardDTOById(Long id, Long userId);
}
