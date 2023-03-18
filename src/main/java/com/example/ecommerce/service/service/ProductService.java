package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<Response> createProduct(CreateProductRequest request);

    ResponseEntity<Response> addProductToCart(Long productId, Integer quantity);

    ResponseEntity<Response> deleteProductById(Long productId);

    ResponseEntity<Response> updateProduct(UpdateProductRequest product);

    ResponseEntity<Response> getProductById(Long productId);

    ResponseEntity<Response> getAllProduct();
}
