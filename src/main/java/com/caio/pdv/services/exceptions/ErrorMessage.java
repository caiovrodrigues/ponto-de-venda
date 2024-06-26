package com.caio.pdv.services.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ErrorMessage(
        Instant timestamp,
        Integer status,
        HttpStatus error,
        String message,
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        List<Map<String, String>> errors
) {
        public ErrorMessage(Instant timestamp, Integer status, HttpStatus error, String message) {
                this(timestamp, status, error, message, null);
        }
}
