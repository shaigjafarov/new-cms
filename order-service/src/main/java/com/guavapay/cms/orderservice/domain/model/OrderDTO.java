package com.guavapay.cms.orderservice.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guavapay.cms.orderservice.domain.enums.Status;
import com.guavapay.cms.orderservice.util.LocalDateTimeSerializer;
import com.guavapay.cms.orderservice.util.LocalDatetimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Status status;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    protected LocalDateTime createdAt;
}
