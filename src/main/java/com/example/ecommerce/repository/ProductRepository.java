package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT * FROM products WHERE LOWER('name') LIKE '%:name%'   
            """, nativeQuery = true)
    public List<Product> findByName(String name);

    @Query(value="""
            SELECT * FROM products p INNER JOIN product_category pc ON p.product_id = pc.product_id
            INNER JOIN categories c ON c.id = pc.categoryId WHERE c.name = :categoryName
            """, nativeQuery = true)
    public List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);
}
