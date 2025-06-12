package com.inventoryapp.service;

import com.inventoryapp.dto.ProductRequest;
import com.inventoryapp.entity.Product;
import com.inventoryapp.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest, UUID userId) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(productRequest.getCategory())
                .userId(userId)
                .build();
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(UUID userId) {
        return productRepository.findByUserId(userId);
    }

    public Product updateProduct(Long id, @Valid ProductRequest productRequest, UUID userId) {
        Product product = productRepository.findById(id)
                .filter(p -> p.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Product not found or not owned by user with id: " + id));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setCategory(productRequest.getCategory());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id, UUID userId) {
        Product product = productRepository.findById(id)
                .filter(p -> p.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Product not found or not owned by user with id: " + id));
        productRepository.delete(product);
    }
}