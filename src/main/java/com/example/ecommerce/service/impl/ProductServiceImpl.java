package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.ProductBriefInfo;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final int PAGE_SIZE = 12;

    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> searchProduct(String keyword, Integer pageNumber) {

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public ResponseEntity<Response> getReviewByProductId(Long productId) {
        Product product = findProductById(productId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get review by product id successfully")
                .data(product.getReviews())
                .build());
    }

    @Override
    public Page<Product> getProductOfStore(Integer pageNumber, Store store) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        return productRepository.findAllByStore(store, pageable);
    }


    @Override
    public ResponseEntity<Response> deleteProductById(Long productId) {
        Product product = findProductById(productId); // check if this product exists
        deleteById(productId);
        return ResponseEntity.ok(Response .builder()
                .status(200)
                .message("Delete product successfully")
                .build());
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
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
        ProductDetailedInfo productDetailedInfo = new ProductDetailedInfo(product);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get product successfully")
                .data(productDetailedInfo)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllProducts(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);

        Page<Product> page = productRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<ProductBriefInfo> productBriefInfos = ProductBriefInfo.from(page.getContent());

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .content(productBriefInfos)
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(pageResponse)
                .build());

    }
}
