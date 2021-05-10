package com.guavapay.cms.orderservice.service;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.CardPeriod;
import com.guavapay.cms.orderservice.domain.enums.CardType;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.domain.repository.OrderRepository;
import com.guavapay.cms.orderservice.exception.NotFoundException;
import com.guavapay.cms.orderservice.exception.OrderAlreadySubmitted;
import com.guavapay.cms.orderservice.exception.OrderNotExist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final Long DUMMY_ID = 1L;
    private static final LocalDateTime localDateTime = LocalDateTime.of(2021, 05, 3, 12, 23);


    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;


    @Test
    void givenUserIdWhenFindOrderDTOListByUserIdThenReturnOrderDto() {
        OrderDTO orderDTO = new OrderDTO(1L, Status.PENDING, LocalDateTime.now());
        List<OrderDTO> orderDTOS = Collections.singletonList(orderDTO);
        when(orderRepository.findOrderDTOListByUserId(DUMMY_ID)).thenReturn(orderDTOS);
        assertEquals(orderService.findOrderDTOListByUserId(DUMMY_ID), orderDTOS);
    }

    @Test
    void givenOrderIdAndUserIdWhenFindOrderDTOListByUserIdThenReturnCardDto() {
        CardDTO cardDTO = new CardDTO(DUMMY_ID, CardType.MC, CardPeriod.ONE_YEAR, "test", true, "test");
        when(orderRepository.findCardDTOByIdAndUserId(DUMMY_ID, DUMMY_ID)).thenReturn(cardDTO);
        assertEquals(orderService.findCardDTOById(DUMMY_ID, DUMMY_ID), cardDTO);
    }

    @Test
    void givenCardDtoAndUserIdWhenCreateOrderThenReturnOrderDto() {
        Order order = new Order(DUMMY_ID, Status.PENDING, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        order.setCreatedAt(localDateTime);
        CardDTO cardDTO = new CardDTO(DUMMY_ID, CardType.MC, CardPeriod.ONE_YEAR, "test", true, "test");
        OrderDTO orderDTO = new OrderDTO(1L, Status.PENDING, localDateTime);
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(orderService.createOrder(cardDTO, DUMMY_ID), orderDTO);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void givenCardDtoAndUserIdWhenUpdateOrderThenReturnOrderDto() {
        Order order = new Order(DUMMY_ID, Status.PENDING, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        order.setCreatedAt(localDateTime);
        CardDTO cardDTO = new CardDTO(DUMMY_ID, CardType.MC, CardPeriod.ONE_YEAR, "test", true, "test");
        OrderDTO orderDTO = new OrderDTO(1L, Status.PENDING, localDateTime);
        when(orderRepository.findByIdAndUserId(order.getId(), order.getUserId())).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(orderService.updateOrder(cardDTO, DUMMY_ID), orderDTO);
    }

    @Test
    void givenOrderIdAndUserIdWhenDeleteOrderNotExist() {
        Order order = new Order(DUMMY_ID, Status.SUBMITTED, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(order);
        Assertions.assertThrows(OrderAlreadySubmitted.class, () -> orderService.deleteOrder(DUMMY_ID, DUMMY_ID));
    }

    @Test
    void givenOrderIdAndUserIdWhenDeleteOrderAlreadySubmitted() {
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        Assertions.assertThrows(OrderNotExist.class, () -> orderService.deleteOrder(DUMMY_ID, DUMMY_ID));
    }

    @Test
    void givenOrderIdAndUserIdWhenDeleteOrderNotFound() {
        Order order = new Order(DUMMY_ID, Status.PENDING, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(order);
        when(orderRepository.updateStatusByIdAndUserId(any(), anyLong(), anyLong())).thenReturn(0);
        Assertions.assertThrows(NotFoundException.class, () -> orderService.deleteOrder(DUMMY_ID, DUMMY_ID));
    }


    @Test
    void givenOrderIdAndUserIdWhenSubmitOrderThenNotExistOrder() {
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(null);
        Assertions.assertThrows(OrderNotExist.class, () -> orderService.submitOrder(DUMMY_ID, DUMMY_ID));
    }

    @Test
    void givenOrderIdAndUserIdWhenSubmitOrderThenExistOrder() {
        Order order = new Order(DUMMY_ID, Status.PENDING, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        order.setCreatedAt(localDateTime);
        Order orderInDb = new Order(DUMMY_ID, Status.SUBMITTED, CardType.MC, CardPeriod.ONE_YEAR,
                "test", true, "test", DUMMY_ID,
                null, null);
        orderInDb.setCreatedAt(localDateTime);
        OrderDTO orderDTO = new OrderDTO(1L, Status.SUBMITTED, localDateTime);
        when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(orderInDb);
        assertEquals(orderService.submitOrder(DUMMY_ID, DUMMY_ID), orderDTO);
    }
}