package com.example.ecommerce.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductCategory;
import com.example.ecommerce.entity.ProductCategory.ProductCategoryId;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.dto.request.product.ProductRequest;
import com.example.ecommerce.model.dto.response.category.CategoryResponse;
import com.example.ecommerce.model.dto.response.product.ProductResponse;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductCategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductResponse> findAll() {
        // TODO Auto-generated method stub
        return productRepository.findAll().stream().map(p -> findById(p.getId())).toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        // TODO Auto-generated method stub
        return ProductResponse.fromProductAndCategories(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id '" + id +"' is not found")), getCategoriesByProductId(id));
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        // TODO Auto-generated method stub
        List<Category> categories = getCategoriesByIds(request.getCategoryIds());

        Product createdProduct = productRepository.save(Product.convertFromProductRequest(request));

        List<ProductCategory> productCategories = categories.stream().map(c -> {
            ProductCategoryId productCategoryId = new ProductCategoryId();
            productCategoryId.setCategoryId(c.getId());
            productCategoryId.setProductId(createdProduct.getId());
            return ProductCategory.builder().productCategoryId(productCategoryId).build();
        }).toList();    

        productCategoryRepository.saveAll(productCategories);

        return ProductResponse.fromProductAndCategories(createdProduct, categories.stream().map(c -> CategoryResponse.fromCategory(c)).toList());

        

    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id '" + id +"' is not found"));
        List<Category> categories = getCategoriesByIds(request.getCategoryIds());

        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStockQuantity(request.getStockQuantity());
        existingProduct.setWeight(request.getWeight());
        existingProduct.setDescription(request.getDescription());
        
        productCategoryRepository.deleteAll(productCategoryRepository.findCategoriesByProductId(id));
        
        List<ProductCategory> productCategories = categories.stream().map(c -> {
            ProductCategoryId productCategoryId = new ProductCategoryId();
            productCategoryId.setCategoryId(c.getId());
            productCategoryId.setProductId(existingProduct.getId());
            return ProductCategory.builder().productCategoryId(productCategoryId).build();
        }).toList();    

        productCategoryRepository.saveAll(productCategories);

        // TODO Auto-generated method stub
        return ProductResponse.fromProductAndCategories(productRepository.save(existingProduct), categories.stream().map(c -> CategoryResponse.fromCategory(c)).toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id '" + id +"' is not found"));
        productCategoryRepository.deleteAll(productCategoryRepository.findCategoriesByProductId(id));

        productRepository.deleteById(existingProduct.getId());

    }

    private List<Category> getCategoriesByIds(List<Long> categoryIds){
        return categoryIds.stream().map(id -> categoryRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Category with id '" + id + "' not found");
        })
        ).toList();
    }

    private List<CategoryResponse> getCategoriesByProductId(Long productId){
        return productCategoryRepository.findCategoriesByProductId(productId).stream()
        .map(c -> CategoryResponse.builder().categoryId(c.getProductCategoryId().getCategoryId())
        .name(categoryRepository.findById(c.getProductCategoryId().getCategoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category with id '" +c.getProductCategoryId().getCategoryId()+ "' is not found")).getName()).build()).toList();
    }

    
}
