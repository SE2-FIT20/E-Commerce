package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.impl.CustomerService;
import com.example.ecommerce.service.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;

    @Autowired
    private  CustomerService customerService;

    @Autowired
    private PromotionService promotionService;

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
    public ResponseEntity<Response> checkout(@RequestBody CheckoutRequest request) {
        User currentCustomer = getCurrentCustomer();
        return customerService.checkout(currentCustomer.getId(), request);
    }

    @PutMapping("/update-status-order")
    public ResponseEntity<Response> updateOrderRequest(@RequestBody UpdateOrderRequest request) {
        User currentCustomer = getCurrentCustomer();
        return customerService.updateOrderRequest(currentCustomer.getId(), request);
    }
    @GetMapping("/orders")
    public ResponseEntity<Response> getOrders(@RequestParam(defaultValue = "0", required = false) Integer page,
                                              @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                              @RequestParam(defaultValue = "ALL",  required = false)  String status,
                                              @RequestParam(defaultValue = "createdAt",  required = false) String filter,
                                              @RequestParam(defaultValue = "desc",  required = false) String sortBy,
                                              @RequestParam(required = false) String from,
                                              @RequestParam(required = false) String to) {

        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }

        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        // the default value for from is 1970, it means that we will get all orders from the beginning
        if (from == null) {
            fromDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else {
            fromDateTime = LocalDateTime.parse(from + "T00:00:00"); // start of the day
        }

        // the default value for to is now, the default value for from is null
        if (to == null) {
            toDateTime = LocalDateTime.now();
        } else {
            toDateTime = LocalDateTime.parse(to + "T23:59:59"); // end of the day
        }

        User currentCustomer = getCurrentCustomer();
        return customerService.getOrders(currentCustomer.getId(), page, elementsPerPage, status, filter, sortBy, fromDateTime, toDateTime);
    }

    @GetMapping("/vouchers-coupons")
    public ResponseEntity<Response> getVouchersCoupons() {
        User currentCustomer = getCurrentCustomer();
        return promotionService.getVouchersCoupons(currentCustomer.getId());
    }

    @PutMapping("/vouchers-coupons/{promotionSetId}")
    public ResponseEntity<Response> saveVoucherOrCoupon(@PathVariable Long promotionSetId) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.saveVoucherOrCoupon(currentCustomer.getId(), promotionSetId);
    }

    @PutMapping("/add-voucher-coupon-to-cart/{promotionId}")
    public ResponseEntity<Response> addVouchersCouponsToCart(@PathVariable Long promotionId) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.addVouchersCouponsToCart(currentCustomer.getId(), promotionId);
    }


    @PutMapping("/remove-voucher-coupon-to-cart/{promotionId}")
    public ResponseEntity<Response> removeVouchersCouponsToCart(@PathVariable Long promotionId) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.removeVouchersCouponsToCart(currentCustomer.getId(), promotionId);
    }

    @GetMapping("/vouchers-coupons-to-add-to-cart")
    public ResponseEntity<Response> getVouchersAndCouponsToAddToCart() {
        User currentCustomer = getCurrentCustomer();
        return customerService.getVouchersAndCouponsToAddToCart(currentCustomer.getId());
    }

    @GetMapping("/check-eligible-to-review/{productId}")
    public ResponseEntity<Response> checkEligibleToReview(@PathVariable Long productId) {
        User currentCustomer = getCurrentCustomer();
        return customerService.checkEligibleToReview(currentCustomer.getId(), productId);
    }

    @GetMapping("/orders-count")
    public ResponseEntity<Response> getOrders(@RequestParam(required = false) String from,
                                              @RequestParam(required = false) String to) {


        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        // the default value for from is 1970, it means that we will get all orders from the beginning
        if (from == null) {
            fromDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else {
            fromDateTime = LocalDateTime.parse(from + "T00:00:00"); // start of the day
        }

        // the default value for to is now, the default value for from is null
        if (to == null) {
            toDateTime = LocalDateTime.now();
        } else {
            toDateTime = LocalDateTime.parse(to + "T23:59:59"); // end of the day
        }

        User currentCustomer = getCurrentCustomer();
        return customerService.countOrders(currentCustomer.getId(), fromDateTime, toDateTime);
    }


    @GetMapping("/payment-information")
    public ResponseEntity<Response> getPaymentInformation() {
        User currentCustomer = getCurrentCustomer();
        return customerService.getPaymentInformation(currentCustomer.getId());
    }


    @PostMapping("/payment-information")
    public ResponseEntity<Response> updatePaymentInformation(@RequestBody AddPaymentInformationRequest request) {
        User currentCustomer = getCurrentCustomer();
        return customerService.addPaymentInformationRequest(currentCustomer.getId(), request);
    }

    @DeleteMapping("/payment-information/{paymentInformationId}")
    public ResponseEntity<Response> deletePaymentInformation(@PathVariable Long paymentInformationId) {
        User currentCustomer = getCurrentCustomer();
        return customerService.deletePaymentInformation(currentCustomer.getId(), paymentInformationId);
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
                            schema = @Schema(implementation = UpdateDeliveryPartnerAccountRequest.class),
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


    @PutMapping("/top-up-balance")
    public ResponseEntity<Response> topUpBalance(@RequestBody TopUpBalanceRequest topUpBalanceRequest) {
        User currentCustomer = getCurrentCustomer();
        return customerService.topUpBalance(currentCustomer.getId(), topUpBalanceRequest);
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
    public ResponseEntity<Response> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest, @PathVariable Long reviewId) {
        User currentCustomer = getCurrentCustomer();
        return customerService.updateReview(currentCustomer.getId(), updateReviewRequest, reviewId);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Response> deleteReview( @PathVariable Long reviewId) {
        User currentCustomer = getCurrentCustomer();
        return customerService.deleteReview(currentCustomer.getId(), reviewId);
    }
}
