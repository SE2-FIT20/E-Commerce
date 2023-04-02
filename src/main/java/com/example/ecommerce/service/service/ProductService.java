package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

//    ResponseEntity<Response> createProduct(Long storeId, CreateProductRequest request);

    ResponseEntity<Response> addProductToCart(Long productId, Integer quantity);

    ResponseEntity<Response> deleteProductByIdAndStoreId(Long storeId, Long productId);
    ResponseEntity<Response> deleteProductById(Long productId);


    ResponseEntity<Response> updateProduct(UpdateProductRequest product);

    ResponseEntity<Response> getProductById(Long productId);

    ResponseEntity<Response> getAllProducts();

    Product findProductById(Long productId);

    void save(Product product);

    List<Product> searchProduct(String keyword);

    ResponseEntity<Response> getReviewByProductId(Long productId);
}
