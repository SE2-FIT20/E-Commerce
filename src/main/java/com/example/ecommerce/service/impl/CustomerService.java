package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.RemoveFromCartRequest;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.ProductService;
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
        List<OrderItem> cart = customer.getCart();
        OrderItemDTO orderItem = orderRequest.getItem();
        Product product = productService.findProductById(orderItem.getProductId());

        // check if the product is already in the cart, if yes, update the quantity
        boolean productInCartAlready = cart.stream().anyMatch(item -> item.getProduct().getId().equals(product.getId()));
        if (productInCartAlready) {
             OrderItem oldItem = cart.stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst().get();

             oldItem.setQuantity(oldItem.getQuantity() + orderItem.getQuantity());

        } else {
            // if not, add the product to the cart
            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(orderItem.getQuantity())
                    .build();
            customer.getCart().add(item);
        }



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
        List<OrderItem> cart = customer.getCart();

        // items from a store will be grouped together
        Map<Long, List<OrderItem>> groupByStoreId = cart.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getStore().getId()));

        for (Map.Entry<Long, List<OrderItem>> entry : groupByStoreId.entrySet()) {
            Store store = storeService.findStoreById(entry.getKey());
            List<OrderItem> items = entry.getValue();

            Order order = Order.builder()
                    .customer(customer)
                    .store(store)
                    .items(items)
                    .status(PENDING)
                    .build();

            orderService.save(order);
        }

        customer.setCart(new ArrayList<>()); // empty the cart of customer after checking out
        customerRepository.save(customer);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Checkout successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> removeFromCart(User currentCustomer, RemoveFromCartRequest removeFromCartRequest) {
        Customer customer = findCustomerById(currentCustomer.getId());
        List<OrderItem> cart = customer.getCart();

        boolean productInCart = cart.stream().anyMatch(item -> item.getProduct().getId().equals(removeFromCartRequest.getProductId()));
        if (productInCart) {
            cart.removeIf(item -> item.getProduct().getId().equals(removeFromCartRequest.getProductId()));
            customerRepository.save(customer);
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Remove from cart successfully")
                    .data(null)
                    .build()
            );
        } else {
            throw new IllegalStateException("Product is not in the cart");
        }

    }
}
