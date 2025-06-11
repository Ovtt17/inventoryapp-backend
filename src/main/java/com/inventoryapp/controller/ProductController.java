package com.inventoryapp.controller;

import com.inventoryapp.dto.ProductRequest;
import com.inventoryapp.entity.Product;
import com.inventoryapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody @Valid ProductRequest productRequest,
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(productService.createProduct(productRequest, email));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(productService.getAllProducts(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest productRequest,
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email
    ) {
        productService.deleteProduct(id, email);
        return ResponseEntity.noContent().build();
    }
}