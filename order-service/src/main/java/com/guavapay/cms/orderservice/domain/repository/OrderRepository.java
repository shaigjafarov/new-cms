package com.guavapay.cms.orderservice.domain.repository;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT new com.guavapay.cms.orderservice.domain.model.OrderDTO(o.id,o.status,o.createdAt) from Order o WHERE o.status<> :status  and o.userId=:userId")
    List<OrderDTO> findOrderDTOListByUserIdAndStatus(Long userId, Status status);

    @Query("SELECT new com.guavapay.cms.orderservice.domain.model.CardDTO(o.cardType,o.cardPeriod,o.cardHolderName,o.urgent,o.codeWord) from Order o where o.id=:id and o.userId=:userId and o.status<> :status")
    CardDTO findCardDTOByIdAndUserIdAndStatus(Long id, Long userId, Status status);
}
