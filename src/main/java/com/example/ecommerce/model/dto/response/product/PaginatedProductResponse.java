package com.example.ecommerce.model.dto.response.product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.ecommerce.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedProductResponse {
    private List<ProductResponse> data;
    private int PageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public static PaginatedProductResponse fromProductResponsePage(Page<ProductResponse> resp){
        return PaginatedProductResponse.builder()
        .data(resp.getContent())
        .PageNumber(resp.getPageable().getPageNumber())
        .pageSize(resp.getPageable().getPageSize())
        .totalElements(resp.getTotalElements())
        .totalPages(resp.getTotalPages())
        .last(resp.isLast())
        .build();
    }
}
