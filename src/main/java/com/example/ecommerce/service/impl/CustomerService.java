package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.RemoveFromCartRequest;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.request.review.CreateReviewRequest;
import com.example.ecommerce.dto.request.review.UpdateReviewRequest;
import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.ProductService;
import com.example.ecommerce.service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.ecommerce.domain.Order.OrderStatus.PENDING;
import static com.example.ecommerce.dto.request.order.AddToCartRequest.*;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final OrderService orderService;

    private final ReviewService reviewService;
    private final StoreService storeService;

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public ResponseEntity<Response> getCustomerInformationById(Long customerId) {
        Customer customer = findCustomerById(customerId);
        CustomerInformation customerInformation = new CustomerInformation(customer);

        Response response = Response.builder()
                .status(200)
                .message("Get customer information successfully")
                .data(customerInformation)
                .build();

        return ResponseEntity.ok(response);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found for id: " + customerId));
    }

    public ResponseEntity<Response> updateAccount(Long id, UpdateCustomerRequest request) {
        Customer customer = findCustomerById(id);

        if (request.getName() != null) customer.setName(request.getName());
        if (request.getAddresses() != null) customer.setAddresses(request.getAddresses());
        if (request.getPhone() != null) customer.setPhoneNumber(request.getPhone());
        if (request.getAvatar() != null) customer.setAvatar(request.getAvatar());

        customerRepository.save(customer);


        Response response = Response.builder()
                .status(200)
                .message("Update customer information successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> addToCart(User user, AddToCartRequest orderRequest) {
        Customer customer = findCustomerById(user.getId());
        Cart cart = customer.getCart();
        OrderItemDTO orderItem = orderRequest.getItem();
        Product product = productService.findProductById(orderItem.getProductId());

        cart.addItem(product, orderItem.getQuantity());


        customerRepository.save(customer);

        Response response = Response.builder()
                .status(200)
                .message("Add to cart successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getCartItems(User currentCustomer) {
        Customer customer = findCustomerById(currentCustomer.getId());

        Response response = Response.builder()
                .status(200)
                .message("Get cart items successfully")
                .data(customer.getCart())
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> checkout(Long customerId) {
        Customer customer = findCustomerById(customerId);
        Cart cart = customer.getCart();


        for (CartStoreItem cartStoreItem : cart.getStores()) {
            Store store = storeService.findStoreById(cartStoreItem.getId());
            List<OrderItem> items = cartStoreItem.getItems();

            Order order = Order.builder()
                    .customer(customer)
                    .store(store)
                    .items(items)
                    .status(PENDING)
                    .build();

            orderService.save(order);
        }

        cart.setStores(new ArrayList<>()); // empty the cart of customer after checking out
        customerRepository.save(customer);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Checkout successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> removeFromCart(User currentCustomer, RemoveFromCartRequest removeFromCartRequest) {
        Customer customer = findCustomerById(currentCustomer.getId());
        Cart cart = customer.getCart();
        Product product = productService.findProductById(removeFromCartRequest.getProductId());
        cart.removeItem(product);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Remove item from cart successfully")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> getOrderDelivering(Long customerId) {
        Customer customer = findCustomerById(customerId);
        List<Order> orders = customer.getOrders();

        List<Order> orderDelivering = orders.stream().filter(order -> order.getStatus() == Order.OrderStatus.DELIVERING
        ).collect(Collectors.toList()
        );

        Response response = Response.builder()
                .status(200)
                .message("Get order delivering successfully")
                .data(orderDelivering)
                .build();

        return ResponseEntity.ok(response);

    }
    public ResponseEntity<Response> getOrderHistory(Long customerId) {
        Customer customer = findCustomerById(customerId);
        List<Order> orders = customer.getOrders();

        List<Order> orderHistory = orders.stream().filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED
        ).collect(Collectors.toList()
        );

        Response response = Response.builder()
                .status(200)
                .message("Get order history successfully")
                .data(orderHistory)
                .build();

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Response> createReview(Long customerId, CreateReviewRequest request) {
        return reviewService.createReview(customerId, request);
    }

    public ResponseEntity<Response> updateReview(Long productId, UpdateReviewRequest updateReviewRequest) {
        return reviewService.updateReview(productId, updateReviewRequest);
    }
}
