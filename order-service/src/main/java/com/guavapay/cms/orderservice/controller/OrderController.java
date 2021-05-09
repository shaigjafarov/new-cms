package com.guavapay.cms.orderservice.controller;

import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@Api(tags = {"Card Order information"})
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderList(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findOrderDTOListByUserId(userId));
    }

    @GetMapping("/details")
    public ResponseEntity<CardDTO> getCardInfo(@RequestParam Long id, @RequestParam Long userId) {
       return ResponseEntity.ok(orderService.findCardDTOById(id,userId));
    }
    @PutMapping("/update")
    public ResponseEntity<OrderDTO> updateOrder( @RequestBody CardDTO cardDTO, @RequestParam Long userId) {
       return ResponseEntity.ok(orderService.updateOrder(cardDTO,userId));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder( @RequestBody CardDTO cardDTO, @RequestParam Long userId) {
       return new  ResponseEntity<>(orderService.createOrder(cardDTO,userId), HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam Long id, @RequestParam Long userId) {
       return   ResponseEntity.ok(orderService.submitOrder(id,userId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteOrder(@RequestParam Long id, @RequestParam Long userId) {
       return   ResponseEntity.ok(orderService.deleteOrder(id,userId));
    }

}
