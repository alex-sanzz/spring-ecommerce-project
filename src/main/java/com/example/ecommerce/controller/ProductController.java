package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.model.dto.request.product.ProductRequest;
import com.example.ecommerce.model.dto.response.product.ProductResponse;
import com.example.ecommerce.service.product.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest product){
        return ResponseEntity.status(201).body(productService.create(product));
    }

    @PutMapping("/{id}")    
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest product){
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")    
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
