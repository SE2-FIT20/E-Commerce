package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.promotion.AddPromotionToCartRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.impl.CustomerService;
import com.example.ecommerce.service.service.PromotionService;
import com.example.ecommerce.service.service.TransactionService;
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
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;

    @Autowired
    private  CustomerService customerService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private TransactionService transactionService;


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

    @GetMapping("/promotions-in-cart")
    public ResponseEntity<Response> getPromotionsInCart() {
        return customerService.getPromotionsInCart(getCurrentCustomer());
    }

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

    @PutMapping("/vouchers-coupons/{promotionId}")
    public ResponseEntity<Response> saveVoucherOrCoupon(@PathVariable Long promotionId) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.saveVoucherOrCoupon(currentCustomer.getId(), promotionId);
    }

    @PutMapping("/add-voucher-coupon-to-cart")
    public ResponseEntity<Response> addVouchersCouponsToCart(@RequestBody AddPromotionToCartRequest request) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.addVouchersCouponsToCart(currentCustomer.getId(), request.getPromotionIds());
    }

    @PutMapping("/remove-voucher-coupon-from-cart/{promotionId}")
    public ResponseEntity<Response> removeVouchersCouponsToCart(@PathVariable Long promotionId) {
        User currentCustomer = getCurrentCustomer();
        return promotionService.removeVouchersCouponsToCart(currentCustomer.getId(), promotionId);
    }



    @PutMapping("/remove-all-voucher-coupon-from-cart")
    public ResponseEntity<Response> removeAllVouchersCouponsToCart() {
        User currentCustomer = getCurrentCustomer();
        return promotionService.removeAllVouchersCouponsToCart(currentCustomer.getId());
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


    @GetMapping("/check-eligible-to-play-mini-game")
    public ResponseEntity<Response> checkEligibleToPlayMiniGame() {
        User currentCustomer = getCurrentCustomer();
        return customerService.checkEligibleToPlayMiniGame(currentCustomer.getId());
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

    @GetMapping("/transactions")
    public ResponseEntity<Response> getAllTransactions(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                                       @RequestParam(defaultValue = "ALL",  required = false)  String status,
                                                       @RequestParam(defaultValue = "createdAt",  required = false) String filter,
                                                       @RequestParam(defaultValue = "desc",  required = false) String sortBy) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }

        User currentCustomer = getCurrentCustomer();
        return transactionService.getAllTransactions(currentCustomer.getId(), page, elementsPerPage, status, filter, sortBy);
    }

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
