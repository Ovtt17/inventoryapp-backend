package com.inventoryapp.controller;

import com.inventoryapp.dto.ProductRequest;
import com.inventoryapp.entity.Product;
import com.inventoryapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody @Valid ProductRequest productRequest,
            @RequestHeader("X-User-ID") UUID userId
    ) {
        return ResponseEntity.ok(productService.createProduct(productRequest, userId));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestHeader("X-User-ID") UUID userId
    ) {
        return ResponseEntity.ok(productService.getAllProducts(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest productRequest,
            @RequestHeader("X-User-ID") UUID userId
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("X-User-ID") UUID userId
    ) {
        productService.deleteProduct(id, userId);
        return ResponseEntity.noContent().build();
    }
}