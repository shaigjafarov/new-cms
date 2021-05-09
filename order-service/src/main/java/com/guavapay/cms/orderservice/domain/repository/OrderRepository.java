package com.guavapay.cms.orderservice.domain.repository;

import com.guavapay.cms.orderservice.domain.entity.Order;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT new com.guavapay.cms.orderservice.domain.model.OrderDTO(o.id,o.status,o.createdAt) from Order o WHERE  o.userId=:userId")
    List<OrderDTO> findOrderDTOListByUserId(Long userId);

    @Query("SELECT new com.guavapay.cms.orderservice.domain.model.CardDTO(o.id,o.cardType,o.cardPeriod,o.cardHolderName,o.urgent,o.codeWord) from Order o where o.id=:id and o.userId=:userId")
    CardDTO findCardDTOByIdAndUserId(Long id, Long userId);

    Order findByIdAndUserId(Long id,Long userId);


    @Modifying
    @Query("update Order o set o.status = :status where o.id = :id and o.userId=:userId")
    int updateStatusByIdAndUserId(Status status, Long id, Long userId);
}
