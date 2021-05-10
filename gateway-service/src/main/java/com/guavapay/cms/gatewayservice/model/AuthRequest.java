package com.guavapay.cms.gatewayservice.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequest {
    @NotBlank
    private String username;

    @NotEmpty(message = "Password must not be null or empty")
    private String password;
}
