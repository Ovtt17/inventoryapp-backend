package com.inventoryapp.service;

import com.inventoryapp.dto.ProductRequest;
import com.inventoryapp.entity.Product;
import com.inventoryapp.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest, String email) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(productRequest.getCategory())
                .userEmail(email)
                .build();
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(String email) {
        return productRepository.findByUserEmail(email);
    }

    public Product updateProduct(Long id, @Valid ProductRequest productRequest, String email) {
        Product product = productRepository.findById(id)
                .filter(p -> p.getUserEmail().equals(email))
                .orElseThrow(() -> new RuntimeException("Product not found or not owned by user with id: " + id));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setCategory(productRequest.getCategory());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id, String email) {
        Product product = productRepository.findById(id)
                .filter(p -> p.getUserEmail().equals(email))
                .orElseThrow(() -> new RuntimeException("Product not found or not owned by user with id: " + id));
        productRepository.delete(product);
    }
}