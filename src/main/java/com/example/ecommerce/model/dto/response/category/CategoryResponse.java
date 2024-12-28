package com.example.ecommerce.model.dto.response.category;

import com.example.ecommerce.entity.Category;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(SnakeCaseStrategy.class)
public class CategoryResponse {
    private Long categoryId;
    private String name;

    public static CategoryResponse fromCategory(Category category){
        return CategoryResponse.builder().categoryId(category.getId()).name(category.getName()).build();
    }
}
