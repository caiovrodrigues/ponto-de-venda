package com.caio.pdv.web.dto;

import jakarta.validation.constraints.NotNull;

public record ItemSaleDTO(
        @NotNull
        Long productId,
        @NotNull
        Integer quantity) {
}
