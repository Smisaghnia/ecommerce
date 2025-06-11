package com.shop.ecommerce.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String email;  // Keine @Email-Validierung (kann je nach Anforderung variieren)

    @NotBlank
    private String password;
}