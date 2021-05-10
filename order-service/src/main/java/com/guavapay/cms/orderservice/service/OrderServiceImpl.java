package com.guavapay.cms.orderservice.service;

import com.guavapay.cms.orderservice.config.RabbitConfiguration;
import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.GeneratedCardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.domain.repository.OrderRepository;
import com.guavapay.cms.orderservice.exception.NotFoundException;
import com.guavapay.cms.orderservice.exception.OrderAlreadySubmitted;
import com.guavapay.cms.orderservice.exception.OrderNotExist;
import com.guavapay.cms.orderservice.mapper.OrderMapper;
import com.guavapay.cms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

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
        order.setStatus(Status.PENDING);
        return OrderMapper.INSTANCE.orderToOrderDTO(orderRepository.save(order));
    }

    @Transactional
    @Override
    public OrderDTO updateOrder(CardDTO cardDTO, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(cardDTO.getId(), userId);
        checkOrderNotNull(orderInDb);
        checkOrderAlreadySubmitted(orderInDb.getStatus());
        Order orderReceived = OrderMapper.INSTANCE.cardDTOtoEntity(cardDTO);
        orderReceived.setCreatedAt(orderInDb.getCreatedAt());
        orderReceived.setStatus(orderInDb.getStatus());
        orderReceived.setUserId(userId);
        Order order = orderRepository.save(orderReceived);
        return OrderMapper.INSTANCE.orderToOrderDTO(order);
    }

    @Transactional
    @Override
    public void deleteOrder(Long id, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(id, userId);
        checkOrderNotNull(orderInDb);
        checkOrderAlreadySubmitted(orderInDb.getStatus());
        checkDeleteOperation(orderRepository.updateStatusByIdAndUserId(Status.DELETED, id, userId));
    }

    @Override
    @Transactional
    public void updateOrderByGeneratedCardService(GeneratedCardDTO generatedCardDTO) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(generatedCardDTO.getOrderId());
            optionalOrder.ifPresent(order -> {
                order.setAccountNumber(generatedCardDTO.getAccountNumber());
                order.setCardNumber(generatedCardDTO.getCardNumber());
                order.setStatus(Status.GENERATED);
                orderRepository.save(order);
            });
        } catch (Exception e) {
            log.error(" Error during updating  order generated card service", e);
        }
    }

    @Override
    public OrderDTO submitOrder(Long id, Long userId) {
        Order orderInDb = orderRepository.findByIdAndUserId(id, userId);
        checkOrderNotNull(orderInDb);
        rabbitTemplate.convertAndSend(RabbitConfiguration.queueSubmitOrder, id);
        orderInDb.setStatus(Status.SUBMITTED);
        return OrderMapper.INSTANCE.orderToOrderDTO(orderRepository.save(orderInDb));
    }


    private void checkOrderNotNull(Order order) {
        if (order == null)
            throw new OrderNotExist("order not exist");
    }

    private void checkOrderAlreadySubmitted(Status status) {
        if (status == Status.SUBMITTED || status == Status.GENERATED)
            throw new OrderAlreadySubmitted();
    }

    private void checkDeleteOperation(int response) {
        if (response == 0)
            throw new NotFoundException("Operation failed.");
    }

}
