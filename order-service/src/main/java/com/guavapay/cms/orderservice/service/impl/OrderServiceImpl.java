package com.guavapay.cms.orderservice.service.impl;

import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.domain.repository.OrderRepository;
import com.guavapay.cms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> findOrderDTOListByUserId(Long userId) {
        return orderRepository.findOrderDTOListByUserIdAndStatus(userId, Status.DELETED);
    }

    @Override
    public CardDTO findCardDTOById(Long id, Long userId) {
        return orderRepository.findCardDTOByIdAndUserIdAndStatus(id,userId,Status.DELETED);
    }
}
