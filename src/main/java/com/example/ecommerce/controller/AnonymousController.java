package com.example.ecommerce.controller;

import com.example.ecommerce.dto.response.Response;
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

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*", maxAge = 3000)
//TODO: get detailed information of product
//TODO: product with categories
public class AnonymousController {

    @Autowired
    private ProductService productService;

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
    public ResponseEntity<Response> getProducts() {
        return productService.getAllProducts();
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

}
