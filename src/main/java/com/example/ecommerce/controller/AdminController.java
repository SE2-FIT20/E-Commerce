package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all account successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get all account successfully!",
                                                "data": [
                                                    {
                                                        "id": 1 ,
                                                        "email": "quangnam@gmail.com",
                                                        "avatar" : "https:jadjkh.com",
                                                        "role" : "CUSTOMER",
                                                        "isLooked" : false
                                                    },
                                                    {
                                                        "id": 2 ,
                                                        "email": "quangnam@gmail.com",
                                                        "avatar" : "https:jadjkh.com",
                                                        "role" : "STORE",
                                                        "isLooked" : false
                                                    },
                                                    {
                                                        "id": 3 ,
                                                        "email": "quangnam@gmail.com",
                                                        "avatar" : "https:jadjkh.com",
                                                        "role" : "DELIVERY_PARTNER",
                                                        "isLooked" : false
                                                    }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get all account failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get all account failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/manage-accounts")
    public ResponseEntity<Response> getAllAccounts() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get account by id successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get account by id successfully!",
                                                "data": {
                                                        "id": 1 ,
                                                        "email": "quangnam@gmail.com",
                                                        "avatar" : "https:jadjkh.com",
                                                        "role" : "CUSTOMER",
                                                        "isLooked" : false
                                                    }
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Get account by id failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found account!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/manage-accounts/{id}")
    public ResponseEntity<Response> getAccountById(@PathVariable @Schema(description = "id of account") Long id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update account successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update account successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Update account failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update account failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/manage-accounts")
    public ResponseEntity<Response> updateAccount(@RequestBody UpdateAccountRequest request) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete account successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete account successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                        ),@ApiResponse(
                    responseCode = "400",
                    description = "Delete account failed!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Delete account failed!",
                                        "data": null
                                    }
                                    """)
                    )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Account not found!",
                                                "data": null
                                            }
                                            """)
                            )
                    )

            }
    )
    @DeleteMapping("/manage-accounts/{id}")
    public ResponseEntity<Response> deleteAccountById(@PathVariable int id) {
        return null;
    }


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
                                                "message": "Get all orders successfully!",
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
                                                "message": "Get all orders failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/manage-products")
    public ResponseEntity<Response> getProducts() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update product successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update product successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Update product failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update product failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/manage-products")
    public ResponseEntity<Response> updateProduct(@RequestBody ProductRequest request) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete product successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete product successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found the account",
                            content = @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the account!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Delete product failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Delete product failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @DeleteMapping("/manage-products/{id}")
    public ResponseEntity<Response> deleteProductById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get feedback successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get feedback for product successfully!",
                                                "data": [{
                                                    "id" = 1,
                                                    "comment": "The app is good",
                                                    "isRead" : true,
                                                    "UserId" : 12
                                                },
                                                {
                                                    "id" = 2,
                                                    "comment": "The app is bad",
                                                    "isRead" : false,
                                                    "UserId" : 35
                                                },
                                                {
                                                    "id" = 3,
                                                    "comment": "The app is ok",
                                                    "isRead" : false,
                                                    "UserId" : 100
                                                }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get feedback failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get feedback for product failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/feedbacks")
    public ResponseEntity<Response> getFeedbacks() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get feedback successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get feedback by id successfully!",
                                                "data": {
                                                    "id" : 1,
                                                    "comment" : "the app is good",
                                                    "isRead" : false,
                                                    "userId": 12
                                                }
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get feedback failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Wrong id of feedback!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found the feedback!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the feedback!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Response> getFeedbacksById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Mark feedback as read successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Mark feedback as read successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found the feedback id!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Wrong id of feedback!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/feedbacks/{id}")
    public ResponseEntity<Response> markFeedbackAsRead(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete feedback successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete feedback successfully!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Delete the feedback failed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Delete the feedback failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found the feedback!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the feedback!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Response> deleteFeedback(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get all promotions successfully",
                                                "data": [
                                                {
                                                    "id" : 1,
                                                    "percent" : 12,
                                                    "storeId" : 35,
                                                    "isGlobal": false
                                                },
                                                {
                                                    "id" : 2,
                                                    "percent" : 10,
                                                    "storeId" : null,
                                                    "isGlobal": true
                                                }
                                                ]
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
                                                "message": "Get all promotions failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/promotions")
    public ResponseEntity<Response> getAllPromotion() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Create promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
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
                                                "message": "Create promotion failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/promotions")
    public ResponseEntity<Response> createPromotion(@RequestBody PromotionRequest promotionRequest) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update promotion successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Update promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update information of promotion failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not found the promotion!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the promotion!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/promotions/{id}")
    public ResponseEntity<Response> updatePromotionById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Delete promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete promotion successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Delete promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Delete promotion failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not found the promotion!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the promotion!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @DeleteMapping("/promotions/{id}")
    public ResponseEntity<Response> deletePromotionById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Create payment gateway successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Create payment gateway successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Create payment gateway failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Create payment gateway failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("app-setting/payment-gateway")
    public ResponseEntity<Response> createPaymentGateway(@RequestBody PaymentGatewayRequest paymentGatewayRequest) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update payment gateway successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update payment gateway successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Update payment gateway failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Wrong information of payment gateway!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("app-setting/payment-gateway/{id}")
    public ResponseEntity<Response> updatePaymentGatewayById() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Delete payment gateway successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete payment gateway successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Delete payment gateway failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Delete payment gateway failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not found the payment gateway!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Not found the payment gateway!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @DeleteMapping("app-setting/payment-gateway/{id}")
    public ResponseEntity<Response> deletePaymentGateway(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get payment gateway successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get payment gateway successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get payment gateway failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Wrong id of payment gateway!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("app-setting/payment-gateway/{id}")
    public ResponseEntity<Response> getPaymentGatewayById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get all payment gateway successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get all payment gateway successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get all payment gateway failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Can't get all payment gateway!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("app-setting/payment-gateway")
    public ResponseEntity<Response> getAllPaymentGateway() {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Create delivery partner successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Create delivery partner successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Create delivery partner failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Wrong information of delivery partner!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("app-setting/delivery-partner")
    public ResponseEntity<Response> createDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update delivery partner successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update delivery partner successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Update delivery partner failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update delivery partner failed!",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not found the delivery partner!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Not found the delivery partner!",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("app-setting/delivery-partner/{id}")
    public ResponseEntity<Response> updateDeliveryPartnerById(@PathVariable int id) {
        return null;
    }

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
                                                    "name": "giao hang tiet kiem" 
                                                    },
                                                    {
                                                    "id" : 3,
                                                    "name": "giao hang 247" 
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
    @GetMapping("app-setting/delivery-partner")
    public ResponseEntity<Response> getAllDeliveryPartner() {
        return null;
    }

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
    @GetMapping("app-setting/delivery-partner/{id}")
    public ResponseEntity<Response> getDeliveryPartnerById(@PathVariable int id) {
        return null;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Delete delivery partner successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Delete delivery partner successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Delete delivery partner failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Wrong id of delivery partner!",
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
    @DeleteMapping("app-setting/delivery-partner/{id}")
    public ResponseEntity<Response> deleteDeliveryPartnerById(@PathVariable int id) {
        return null;
    }
}
