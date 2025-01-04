package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.model.dto.request.product.ProductRequest;
import com.example.ecommerce.model.dto.response.product.PaginatedProductResponse;
import com.example.ecommerce.model.dto.response.product.ProductResponse;
import com.example.ecommerce.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PaginatedProductResponse> findAll(@RequestParam(required=false) String name, @RequestParam(defaultValue = "0") int page, 
    @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id;asc") String[] sorts){
        List<Order> orders = new ArrayList<>();
        for(String sort : sorts){
            String[] els = sort.split(";");
            orders.add(new Order(getSortDirection(els[1]), els[0]));
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by(orders));

        return ResponseEntity.ok(productService.findAll(pageable, name));
    }

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

    private Direction getSortDirection(String drt){
        if(drt.equalsIgnoreCase("desc")){
            return Direction.DESC;
        }else{
            return Direction.ASC;
        }
    }
}
