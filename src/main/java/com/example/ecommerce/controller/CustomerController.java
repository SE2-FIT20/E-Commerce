package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.CreateReviewRequest;
import com.example.ecommerce.dto.request.RemoveFromCartRequest;
import com.example.ecommerce.dto.request.UpdateAccountRequest;
import com.example.ecommerce.dto.request.UpdateReviewRequest;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@CrossOrigin(value = "*", maxAge = 3000)
public class CustomerController {
    private final CustomerService customerService;
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
    public ResponseEntity<Response> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        User currentCustomer = getCurrentCustomer();
        return customerService.addToCart(currentCustomer,addToCartRequest);
    }

    @GetMapping("/preview-cart")
    public ResponseEntity<Response> previewCart() {
        return customerService.previewCart(getCurrentCustomer());
    }




    @PostMapping("/remove-from-cart")
    public ResponseEntity<Response> removeFromCart(@RequestBody RemoveFromCartRequest removeFromCartRequest) {
        User currentCustomer = getCurrentCustomer();
        return customerService.removeFromCart(currentCustomer,removeFromCartRequest);
    }
    @GetMapping("/cart")
    public ResponseEntity<Response> getCartItems() {
        return customerService.getCartItems(getCurrentCustomer());
    }

    @Operation(
            summary = "Checkout"
    )
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
        User currentCustomer = getCurrentCustomer();
        return customerService.checkout(currentCustomer.getId());
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
    @Operation(
            summary = "Change information of account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateAccountRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "fullName": "Nguyen Van A",
                                        "email": "NguyenVanA@gmail.com"
                                        "address": "Ha Noi",
                                        "phoneNumber": "0123456789"
                                    }
                                    """)
                    )
            )

    )
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
        User currentCustomer = getCurrentCustomer();
        return customerService.updateAccount(currentCustomer.getId(), accountRequest);
    }
    @Operation (
            summary = "Get information of account"
    )
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
        User currentCustomer = getCurrentCustomer();
        return customerService.getCustomerInformationById(currentCustomer.getId());
    }

    public User getCurrentCustomer() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/review")
    public ResponseEntity<Response> createReview(@RequestBody CreateReviewRequest reviewRequest){
        User currentCustomer = getCurrentCustomer();
        return customerService.createReview(currentCustomer, reviewRequest);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Response> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequest updateReviewRequest) {
        return customerService.updateReview(reviewId, updateReviewRequest);
    }

    @GetMapping("/review")
    public ResponseEntity<Response> getReviewByProduct(@RequestBody Product product) {
        return customerService.getReviewByProduct(product);
    }
}
