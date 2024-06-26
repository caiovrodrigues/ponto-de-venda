package com.caio.pdv.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaleRequestDTO(
        @NotNull
        Long userId,
        @Valid
        @NotEmpty
        List<ItemSaleDTO> items) {
}

