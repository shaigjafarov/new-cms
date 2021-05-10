package com.guavapay.cms.orderservice.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guavapay.cms.orderservice.util.LocalDateTimeSerializer;
import com.guavapay.cms.orderservice.util.LocalDatetimeDeserializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime updatedAt;



}