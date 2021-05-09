package com.guavapay.cms.orderservice.controller;

import com.guavapay.cms.orderservice.domain.model.CardDTO;
import com.guavapay.cms.orderservice.domain.model.OrderDTO;
import com.guavapay.cms.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@Api(tags = {"Card Order information"})
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orderList/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderList(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findOrderDTOListByUserId(userId));
    }

    @GetMapping("/cardInfo")
    public ResponseEntity<CardDTO> getCardInfo(@RequestParam Long id, @RequestParam Long userId) {
       return ResponseEntity.ok(orderService.findCardDTOById(id,userId));
    }

//    @PutMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void updateEmployee(@RequestParam Long id, @RequestBody EmployeeDto employeeDto) {
//        employeeService.updateEmployee(id, employeeDto);
//    }

}
