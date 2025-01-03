package com.example.ecommerce.model.dto.request.product;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@JsonNaming(SnakeCaseStrategy.class)
public class ProductRequest {
    @NotBlank(message = "Name mustn't empty")
    private String name;

    @NotNull(message = "Price mustn't empty")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @NotBlank(message = "Description mustn't empty")
    private String description;

    @NotNull(message = "Stock Quantity mustn't empty")
    @Positive(message = "Stock Quantity must be a positive number")
    private Integer stockQuantity;

    @NotNull(message = "Weight mustn't empty")
    @Positive(message = "Weight must be a positive number")
    private BigDecimal weight;

    private List<Long> categoryIds;
}
