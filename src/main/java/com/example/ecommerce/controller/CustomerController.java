package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddOrderRequest;
import com.example.ecommerce.dto.response.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add product to cart successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Add product to cart successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Add product to cart failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Add product to cart failed",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Product not found",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/add-to-cart")
    public ResponseEntity<Response> addToCart(@RequestBody AddOrderRequest orderRequest) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Checkout successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Checkout successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Checkout failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Checkout failed",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Product not found",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/checkout")
    public ResponseEntity<Response> checkout() {
        return null;
    }

    /* This is optional as the result of team discussion
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get order history successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get order history successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get order history failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get order history failed",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Order not found",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 500,
                                                "message": "Internal server error",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/order-history")
    public ResponseEntity<Response> getOrderHistory() {
        return null;
    }
*/
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update account information successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update account information successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Update account information failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update account information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Account not found",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/account")
    public ResponseEntity<Response> updateAccount(@RequestBody UpdateCustomerRequest accountRequest) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get account information successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get account information successfully",
                                                "data": {
                                                    "id": 12,
                                                    "name": "Quan",
                                                    "email": "quando@gmail.com",
                                                    "address": "12b Street A District 1",
                                                    "phoneNumber": "2058821021"
                                                }
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get account information failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get account information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Account not found",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/account")
    public ResponseEntity<Response> getAccount() {
        return null;
    }
}
