package com.guavapay.cms.orderservice.service;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findOrderDTOListByUserId(Long userId);

    CardDTO findCardDTOById(Long id, Long userId);

    OrderDTO createOrder(CardDTO cardDTO, Long userId);

    OrderDTO updateOrder(CardDTO cardDTO, Long userId);

    Boolean deleteOrder(Long id, Long userId);

    void updateOrderByGeneratedCardService(GeneratedCardDTO generatedCardDTO);

    OrderDTO submitOrder(Long id, Long userId);
}
