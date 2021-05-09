package com.guavapay.cms.orderservice.mapper;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderDTOtoEntity(OrderDTO orderDTO);


    OrderDTO orderToOrderDTO(Order order);

    CardDTO orderToCardDTO(Order order);

    Order cardDTOtoEntity(CardDTO cardDTO);


}