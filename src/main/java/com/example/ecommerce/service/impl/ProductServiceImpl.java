package com.example.ecommerce.service.impl;

import ch.qos.logback.core.status.Status;
import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.ProductBriefInfo;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.StoreRepository;
import com.example.ecommerce.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public ResponseEntity<Response> searchProduct(String keyword, Integer pageNumber, Integer elementsPerPage) {

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, pageable);

        PageResponse pageResponse = PageResponse.builder()
                .content(ProductBriefInfo.from(products.getContent()))
                .totalPages(products.getTotalPages())
                .size(products.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Search product successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> getReviewByProductId(Long productId) {
        //TODO: paging, filter
        Product product = findProductById(productId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get review by product id successfully")
                .data(product.getReviews())
                .build());
    }

    @Override
    public Page<Product> getProductOfStore(Integer page, Integer elementsPerPage, Store store) {
        Pageable pageable = PageRequest.of(page, elementsPerPage);
        return productRepository.findAllByStore(store, pageable);
    }

    @Override
    public ResponseEntity<Response> getAllProductCategories() {
        Category[] categories = Category.values();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all product categories successfully")
                .data(categories)
                .build());
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
    public ResponseEntity<Response> getAllProducts(Integer pageNumber, Integer elementsPerPage, String category, Long storeId, String filter, String sortBy, String status) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Direction.valueOf(sortBy.toUpperCase()), filter));

        Store store = null;
        Category categoryEnum = null;

        if (storeId != null && storeId != 0) {
            store = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException("Store not found"));
        }

        if (category != null && !category.equals("all")) {
            categoryEnum = Category.valueOf(category.toUpperCase());;
        }

        Page<Product> page;

        if (status.toUpperCase().equals("ALL")
            || status.toUpperCase().equals("SOLD_OUT")
        ) {
            Product product = new Product();

            if (categoryEnum != null) {
                product.setCategory(categoryEnum);
            }

            if (store != null) {
                product.setStore(store);
            }

            if (status.toUpperCase().equals("SOLD_OUT")){
                product.setQuantity(0);
            }

            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            Example<Product> example = Example.of(product, matcher);
            page = productRepository.findAll(example, pageable);

        } else {
            // when finding all product that have quantity > 0
            if (categoryEnum != null && store != null) {
                page = productRepository.findAllByStoreAndCategoryAndQuantityGreaterThan(store, categoryEnum, 0, pageable);
            } else if (categoryEnum != null ) {
                page = productRepository.findAllByCategoryAndQuantityGreaterThan(categoryEnum, 0, pageable);
            } else if (store != null) {
                page = productRepository.findAllByStoreAndQuantityGreaterThan(store, 0, pageable);
            } else {
                page = productRepository.findAllByQuantityGreaterThan(0, pageable);
            }
        }


        List<ProductBriefInfo> productBriefInfos = ProductBriefInfo.from(page.getContent());
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .size(page.getSize())
                .content(productBriefInfos)
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(pageResponse)
                .build());

    }

}
