package com.example.ecommerce.service.product;

import java.util.List;
import java.util.Optional;

import com.example.ecommerce.model.dto.request.product.ProductRequest;
import com.example.ecommerce.model.dto.response.product.ProductResponse;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}
