package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.SearchByNameResult;
import com.example.ecommerce.dto.response.StoreDetailedInfo;
import com.example.ecommerce.service.impl.StoreService;
import com.example.ecommerce.service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*", maxAge = 3000)
//TODO: get detailed information of product
//TODO: product with categories
public class AnonymousController {

    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;

    @Operation(
            summary = "Get all products"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all products successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get all products successfully!",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "name" : "iphone15",
                                                        "description" : "best iphone",
                                                        "category": "electronic",
                                                        "price" : 999.99,
                                                        "image" : "https:link.com"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name" : "apple",
                                                        "description" : "best fruit",
                                                        "category": "food",
                                                        "price" : 90,
                                                        "image" : "https:link.com"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "name" : "t-shirt",
                                                        "description" : "best shirt",
                                                        "category": "fashion",
                                                        "price" : 12,
                                                        "image" : "https:link.com"
                                                    }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get all products failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get all products failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/products")
    public ResponseEntity<Response> getProducts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer elementsPerPage) {
        return productService.getAllProducts(page);
    }

//    public ResponseEntity<Response> getProductsByCategory(@RequestParam String category, @RequestParam(defaultValue = "0") Integer page) {
//        return productService.getProductsByCategory(category, page);
//    }

    @GetMapping("/products/{storeId}")
    public ResponseEntity<Response> getProductsOfStore(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer elementsPerPage, @PathVariable Long storeId) {
        return storeService.getProductByStore(page, storeId);
    }
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get product by id successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Get product by id successfully",
                "data": {
                    "id": 1,
                    "name" : "iphone15",
                    "description" : "best iphone",
                    "category": "electronic",
                    "price" : 999.99,
                    "image" : "https:link.com"
                }
            }
            """))), @ApiResponse(responseCode = "400", description = "Get product by id failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get product failed",
                "data": null
            }
            """)))

    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }


    @GetMapping("/search")
    public ResponseEntity<Response> searchProduct(@RequestParam String keyword, @RequestParam(defaultValue = "0") Integer page) {
        List<Product> products =  productService.searchProduct(keyword, page);
        List<Store> stores = storeService.searchStore(keyword);

        List<ProductDetailedInfo> productDetailedInfos = ProductDetailedInfo.from(products);
        List<StoreDetailedInfo> storeDetailedInfos = StoreDetailedInfo.from(stores);

        SearchByNameResult searchByNameResult = SearchByNameResult.builder()
                .products(productDetailedInfos)
                .stores(storeDetailedInfos)
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Search successfully!")
                .data(searchByNameResult)
                .build());
    }
//
//    @GetMapping("review")
//    public ResponseEntity<Response> getAllReviewByProduct() {
//
//    }

    @GetMapping("/review/{productId}")
    public ResponseEntity<Response> getReviewByProduct(@PathVariable Long productId) {
        return productService.getReviewByProductId(productId);
    }
}
