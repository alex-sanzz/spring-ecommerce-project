package com.example.ecommerce.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.ecommerce.model.dto.request.product.ProductRequest;
import com.example.ecommerce.model.dto.response.product.PaginatedProductResponse;
import com.example.ecommerce.model.dto.response.product.ProductResponse;

public interface ProductService {

    List<ProductResponse> findAll();

    PaginatedProductResponse  findAll(PageRequest pageRequest, String name);

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}
