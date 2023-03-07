package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<Response> createProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create product successfully")
                .data(product)
                .build());
    }

    @Override
    public ResponseEntity<Response> addProductToCart(Long productId, Integer quantity) {
        Product product = productRepository.getById(productId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Add product to cart successfully")
                        .data(product)
                        .data(quantity)
                .build());
    }



    @Override
    public ResponseEntity<Response> deleteProductById(Long productId) {
        productRepository.deleteById(productId);
        return ResponseEntity.ok(Response .builder()
                .status(200)
                .message("Delete product successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> updateProduct(Long productId, UpdateProductRequest product) {
        Product updateProduct = productRepository.getById(productId);
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setImage(product.getImage());
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update product successfully")
                .data(updateProduct)
                .build());
    }


    @Override
    public ResponseEntity<Response> getProductById(Long productId) {
        Product product = productRepository.getById(productId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get product successfully")
                .data(product)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all product successfully")
                .data(products)
                .build());
    }

}
