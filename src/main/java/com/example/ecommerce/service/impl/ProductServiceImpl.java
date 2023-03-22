package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
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
    public ResponseEntity<Response> createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .images(request.getImages())
                .build();
        productRepository.save(product);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create product successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> addProductToCart(Long productId, Integer quantity) {
        Product product = findProductById(productId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Add product to cart successfully")
                        .data(product)
                        .data(quantity)
                .build());
    }

    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    @Override
    public ResponseEntity<Response> deleteProductById(Long productId) {
        //TODO: check if this product belongs to the store

        Product product = findProductById(productId);
        productRepository.delete(product);

        return ResponseEntity.ok(Response .builder()
                .status(200)
                .message("Delete product successfully")
                .build());
    }


    @Override
    public ResponseEntity<Response> updateProduct(UpdateProductRequest request) {
        //TODO: check if this product belongs to the store
        Product product = findProductById(request.getProductId());


        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getImages() != null) product.setImages(request.getImages());

        productRepository.save(product);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update product successfully")
                .data(null)
                .build());
    }


    @Override
    public ResponseEntity<Response> getProductById(Long productId) {
        Product product = findProductById(productId);
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
