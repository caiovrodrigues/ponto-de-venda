package com.caio.pdv.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
