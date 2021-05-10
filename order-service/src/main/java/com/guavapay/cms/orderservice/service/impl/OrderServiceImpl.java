package com.guavapay.cms.orderservice.service.impl;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.domain.repository.OrderRepository;
import com.guavapay.cms.orderservice.exception.OrderAlreadySubmitted;
import com.guavapay.cms.orderservice.exception.OrderNotExist;
import com.guavapay.cms.orderservice.mapper.OrderMapper;
import com.guavapay.cms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> findOrderDTOListByUserId(Long userId) {
        return orderRepository.findOrderDTOListByUserId(userId);
    }

    @Override
    public CardDTO findCardDTOById(Long id, Long userId) {
        return orderRepository.findCardDTOByIdAndUserId(id, userId);
    }

    @Transactional
    @Override
    public OrderDTO createOrder(CardDTO cardDTO, Long userId) {
        Order order = OrderMapper.INSTANCE.cardDTOtoEntity(cardDTO);
        order.setUserId(userId);
        order.setStatus(Status.GENERATED);
        return OrderMapper.INSTANCE.orderToOrderDTO(orderRepository.save(order));
    }

    @Transactional
    @Override
    public OrderDTO updateOrder(CardDTO cardDTO, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(cardDTO.getId(), userId);
        if (orderInDb == null) {
            throw new OrderNotExist();
        } else if (orderInDb.getStatus().equals(Status.SUBMITTED)) {
            throw new OrderAlreadySubmitted();
        }
        Order orderReceived = OrderMapper.INSTANCE.cardDTOtoEntity(cardDTO);
        orderReceived.setCreatedAt(orderInDb.getCreatedAt());
        orderReceived.setStatus(orderInDb.getStatus());
        orderReceived.setUserId(userId);
        Order order = orderRepository.save(orderReceived);
        return OrderMapper.INSTANCE.orderToOrderDTO(order);
    }

    @Transactional
    @Override
    public Boolean deleteOrder(Long id, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(id, userId);
        if (orderInDb == null) {
            throw new OrderNotExist();
        } else if (orderInDb.getStatus().equals(Status.SUBMITTED)) {
            throw new OrderAlreadySubmitted();
        }

        if (orderRepository.updateStatusByIdAndUserId(Status.DELETED, id, userId) == 0)
            throw new IllegalArgumentException("Operation failed.");
        return true;
    }

    @Override
    @Transactional
    public void updateOrderByGeneratedCardService(GeneratedCardDTO generatedCardDTO) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(generatedCardDTO.getOrderId());
            optionalOrder.ifPresent(order -> {
                order.setAccountNumber(generatedCardDTO.getAccountNumber());
                order.setCardNumber(generatedCardDTO.getCardNumber());
                orderRepository.save(order);
            });
        } catch (Exception e) {
            log.error(" Error during updating  order generated card service", e);
        }
    }

    @Override
    public OrderDTO submitOrder(Long id, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(id, userId);
        if (orderInDb == null) {
            throw new OrderNotExist("order not exist");
        }
        orderInDb.setStatus(Status.SUBMITTED);
        return OrderMapper.INSTANCE.orderToOrderDTO(orderRepository.save(orderInDb));
    }

}
