package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.SearchByNameResult;
import com.example.ecommerce.dto.response.StoreDetailedInfo;
import com.example.ecommerce.service.impl.StoreService;
import com.example.ecommerce.service.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnonymousController {

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @Autowired
    private SearchService searchService;
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
    public ResponseEntity<Response> getProducts(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                                @RequestParam(defaultValue = "all",  required = false)  String category,
                                                @RequestParam(defaultValue = "0",  required = false) Long storeId,
                                                @RequestParam(defaultValue = "name",  required = false) String filter,
                                                @RequestParam(defaultValue = "asc",  required = false) String sortBy,
                                                @RequestParam(defaultValue = "all", required = false) String status
                                                ) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }
        return productService.getAllProducts(page, elementsPerPage, category, storeId, filter, sortBy, status);
    }

//    @GetMapping("/products/{storeId}")
//    public ResponseEntity<Response> getProductsOfStore(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer elementsPerPage, @PathVariable Long storeId) {
//        return storeService.getProductByStore(page, storeId);
//    }
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


    @GetMapping("/search-products")
    public ResponseEntity<Response> searchProduct(@RequestParam String keyword,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "0") Integer elementsPerPage) {

        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }

        searchService.saveSearchHistory(getCurrentUser(), keyword);

        return productService.searchProduct(keyword, page, elementsPerPage);

    }

    @GetMapping("/search-stores")
    public ResponseEntity<Response> searchStores(@RequestParam String keyword,
                                                 @RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "0") Integer elementsPerPage) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }

        searchService.saveSearchHistory(getCurrentUser(), keyword);
        return storeService.searchStore(keyword, page, elementsPerPage);

        // save the search history
    }



    @GetMapping("/promotions/{code}")
    public ResponseEntity<Response> getPromotionByCode(@PathVariable String code) {
        return promotionService.getPromotionByCode(code);
    }


    @GetMapping("/store-information/{storeId}")
    public ResponseEntity<Response> getStoreInfoById(@PathVariable Long storeId) {

        return storeService.getStoreInformationById(storeId);
    }

    @GetMapping("/reviews/{productId}")
    public ResponseEntity<Response> getReviewByProduct(@PathVariable Long productId,
                                                       @RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                                       @RequestParam(defaultValue = "createdAt",  required = false) String filter,
                                                       @RequestParam(defaultValue = "desc",  required = false) String sortBy) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }



        return productService.getReviewByProductId(productId, page, elementsPerPage, filter, sortBy);
    }

    @GetMapping("/product-categories")
    public ResponseEntity<Response> getALlProductCategories() {
        return productService.getAllProductCategories();
    }

    @Operation(
            summary = "Get all delivery partners",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get list delivery partner successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get list delivery partner successfully",
                                                "data": [
                                                    {
                                                    "id" : 1,
                                                    "name": "giao hang nhanh"
                                                    },
                                                    {
                                                    "id" : 2,
                                                    "name": "giao hang tiet kiem",
                                                    },
                                                    {
                                                    "id" : 3,
                                                    "name": "giao hang 247",
                                                    }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get list delivery partner failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Can't get list delivery partner!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/delivery-partners")
    public ResponseEntity<Response> getAllDeliveryPartners(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(defaultValue = "0", required = false) Integer elementsPerPage) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }
        return deliveryPartnerService.getAllDeliveryPartners(page, elementsPerPage);
    }

    @Operation(
            summary = "Get delivery partner by id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get delivery partner successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get delivery partner successfully",
                                                "data": {
                                                    "id" : 1,
                                                    "name": "giao hang nhanh"
                                                }
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get delivery partner failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get delivery partner failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not found delivery partner!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found delivery partner!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("delivery-partners/{id}")
    public ResponseEntity<Response> getDeliveryPartnerById(@PathVariable @Schema(description = "id of delivery partner") Long id) {
        return deliveryPartnerService.getDeliveryPartnerById(id);
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search-history")
    public ResponseEntity<Response> getSearchHistory() {
        User user = getCurrentUser();
        return searchService.getLatestSearchesByUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/search-history/{searchId}")
    public ResponseEntity<Response> deleteSearchHistory(@PathVariable Long searchId) {
        User user = getCurrentUser();
        return searchService.deleteSearchById(user, searchId);
    }





    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
