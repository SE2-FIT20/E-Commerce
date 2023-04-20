package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {


    ResponseEntity<Response> deleteProductById(Long productId);


    ResponseEntity<Response> updateProduct(UpdateProductRequest product);

    ResponseEntity<Response> getProductById(Long productId);

    ResponseEntity<Response> getAllProducts(Integer pageNumber, Integer elementsPerPage, String category, Long storeId, String filter, String sortBy, String status);

    Product findProductById(Long productId);

    void save(Product product);

    ResponseEntity<Response> searchProduct(String keyword, Integer pageNumber, Integer elementsPerPage);

    void deleteById(Long productId);
    ResponseEntity<Response> getReviewByProductId(Long productId, Integer page, Integer elementsPerPage, String filter, String sortBy);


    Page<Product> getProductOfStore(Integer page, Integer elementsPerPage, Store store);

    ResponseEntity<Response> getAllProductCategories();

    List<Product> findProductsByIds(List<Long> productIds);


    void saveAll(List<Product> products);

}
