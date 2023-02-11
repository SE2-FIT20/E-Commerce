package com.example.ecommerce.controller;


import com.example.ecommerce.dto.request.AccountRequest;
import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.request.PromotionRequest;
import com.example.ecommerce.dto.response.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/store")
public class StoreController {
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Get all products successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Get all products successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Get all products failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Get all products failed",
                                        "data": null
                                    }
                                    """)
                    )
            )

    })
    @GetMapping("/products")
    public ResponseEntity<Response> getProducts() {
        return null;
    }

    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Create product successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Create product by id successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Create product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Create product failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/products")
    public ResponseEntity<Response> createProduct(@RequestBody ProductRequest productRequest) {
        return null;
    }

    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Update product successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Update product by id successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Update product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Update product failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/products")
    public ResponseEntity<Response> updateProduct(@RequestBody ProductRequest productRequest) {
        return null;
    }

    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Delete product successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Delete product by id successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Delete product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Delete product failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @DeleteMapping("/products")
    public ResponseEntity<Response> deleteProduct(@RequestBody ProductRequest productRequest) {
        return null;
    }

    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Get order successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Get order successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Get order failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Get order failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/orders")
    public ResponseEntity<Response> getOrders() {
        return null;
    }

    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Update order successfully!",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema (implementation = Response.class),
                            examples = @ExampleObject (value = """
                                    {
                                        "status": 200,
                                        "message": "Update order successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Update order failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Update order failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/orders")
    public ResponseEntity<Response> updateOrder() {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse (responseCode = "200", description = "Get sale report successfully!",
                            content = @Content (mediaType = "application/json",
                                    schema = @Schema (implementation = Response.class),
                                    examples = @ExampleObject (value = """
                                            {
                                                "status": 200,
                                                "message": "Get sale report successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get sale report failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get sale report failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/sale-report")
    public ResponseEntity<Response> getSaleReport() {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse (responseCode = "200", description = "Get promotion successfully!",
                            content = @Content (mediaType = "application/json",
                                    schema = @Schema (implementation = Response.class),
                                    examples = @ExampleObject (value = """
                                            {
                                                "status": 200,
                                                "message": "Get promotion successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get promotion failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/promotion")
    public ResponseEntity<Response> getPromotion() {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse (responseCode = "200", description = "Create promotion successfully!",
                            content = @Content (mediaType = "application/json",
                                    schema = @Schema (implementation = Response.class),
                                    examples = @ExampleObject (value = """
                                            {
                                                "status": 200,
                                                "message": "Create promotion successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Create promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Create promotion failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/promotion")
    public ResponseEntity<Response> createPromotion(@RequestBody PromotionRequest promotionRequest) {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse (responseCode = "200", description = "Update store information successfully!",
                            content = @Content (mediaType = "application/json",
                                    schema = @Schema (implementation = Response.class),
                                    examples = @ExampleObject (value = """
                                            {
                                                "status": 200,
                                                "message": "Update store account successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Update store information failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update store information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/account")
    public ResponseEntity<Response> updateAccountInformation(@RequestBody AccountRequest accountRequest) {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse (responseCode = "200", description = "Get store information successfully!",
                            content = @Content (mediaType = "application/json",
                                    schema = @Schema (implementation = Response.class),
                                    examples = @ExampleObject (value = """
                                            {
                                                "status": 200,
                                                "message": "Get store account successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get store information failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get store information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/account")
    public ResponseEntity<Response> getAccountInformation() {
        return null;
    }
}
