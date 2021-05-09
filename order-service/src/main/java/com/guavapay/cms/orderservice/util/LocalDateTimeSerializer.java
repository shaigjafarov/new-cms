package com.guavapay.cms.orderservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        String s = value.format(DATE_FORMATTER);
        gen.writeString(s);

    }
}