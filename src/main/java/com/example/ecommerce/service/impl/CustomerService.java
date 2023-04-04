package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.CreateReviewRequest;
import com.example.ecommerce.dto.request.RemoveFromCartRequest;
import com.example.ecommerce.dto.request.UpdateReviewRequest;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.response.CartStoreItem;
import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.service.DeliveryPartnerService;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.ProductService;
import com.example.ecommerce.service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerce.domain.Order.OrderStatus.DELIVERED;
import static com.example.ecommerce.domain.Order.OrderStatus.PENDING;
import static com.example.ecommerce.dto.request.order.AddToCartRequest.OrderItemDTO;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final OrderService orderService;
    private final DeliveryPartnerService deliveryPartnerService;
    private final StoreService storeService;
    private final ReviewService reviewService;

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public ResponseEntity<Response> getCustomerInformationById(Long customerId) {
        Customer customer = findCustomerById(customerId);
        CustomerInformation customerInformation = new CustomerInformation(customer);

        Response response = Response.builder().status(200).message("Get customer information successfully").data(customerInformation).build();

        return ResponseEntity.ok(response);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found for id: " + customerId));
    }

    public ResponseEntity<Response> updateAccount(Long id, UpdateCustomerRequest request) {
        Customer customer = findCustomerById(id);

        if (request.getName() != null) customer.setName(request.getName());
        if (request.getAddresses() != null) customer.setAddresses(request.getAddresses());
        if (request.getPhone() != null) customer.setPhoneNumber(request.getPhone());
        if (request.getAvatar() != null) customer.setAvatar(request.getAvatar());

        customerRepository.save(customer);


        Response response = Response.builder().status(200).message("Update customer information successfully").data(null).build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> addToCart(User user, AddToCartRequest orderRequest) {
        Customer customer = findCustomerById(user.getId());
        Cart cart = customer.getCart();
        OrderItemDTO orderItem = orderRequest.getItem();
        Product product = productService.findProductById(orderItem.getProductId());

        cart.addItem(product, orderItem.getQuantity());


        customerRepository.save(customer);

        Response response = Response.builder().status(200).message("Add to cart successfully").data(null).build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getCartItems(User currentCustomer) {
        Customer customer = findCustomerById(currentCustomer.getId());
        Cart cart = customer.getCart();
        Response response = Response.builder().status(200).message("Get cart items successfully").data(cart.getItems()).build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<Response> checkout(Long customerId) {
        Customer customer = findCustomerById(customerId);
        Cart cart = customer.getCart();

        DeliveryPartner deliveryPartner = deliveryPartnerService.findDeliveryPartnerById(1L);

        // items in the cart are grouped into group by store
        for (CartStoreItem cartStoreItem : cart.getItems()) {
            Store store = storeService.findStoreById(cartStoreItem.getStore().getId());
            List<OrderItem> items = cartStoreItem.getItems();

            Order order = Order.builder()
                    .customer(customer)
                    .store(store)
                    .items(items)
                    .status(PENDING)
                    .createdAt(LocalDateTime.now())
                    .deliveryPartner(deliveryPartner)
                    .build();

            System.out.println("id: ");
            System.out.println(order.getId());
            // order is the pwning side of the relationship,
            // and it has the customer field, save it to db will make it appear to the customer's order list
            orderService.save(order);

            // this line is not really necessary,
            // but it's good to for understanding that the orders will be savd in the order list of customer,
            // after it is delivered it will be moved to the oldOrder list
            customer.getOrders().add(order);
        }

        cart.setItems(new ArrayList<>()); // empty the cart of customer after checking out
        customerRepository.save(customer);
        return ResponseEntity.ok(Response.builder().status(200).message("Checkout successfully").data(null).build());
    }

    public ResponseEntity<Response> removeFromCart(User currentCustomer, RemoveFromCartRequest removeFromCartRequest) {
        Customer customer = findCustomerById(currentCustomer.getId());
        Cart cart = customer.getCart();
        Product product = productService.findProductById(removeFromCartRequest.getProductId());
        cart.removeItem(product);

        customerRepository.save(customer);
        return ResponseEntity.ok(
                Response.builder()
                        .status(200)
                        .message("Remove item from cart successfully")
                        .data(null)
                        .build()
        );

    }

    public ResponseEntity<Response> previewCart(User currentCustomer) {
        Customer customer = findCustomerById(currentCustomer.getId());
        Cart cart = customer.getCart();

        List<OrderItem> previewList = cart.getOrderItemsPreview();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get cart preview successfully")
                .data(previewList).build()
        );
    }

    public ResponseEntity<Response> createReview(User currentCustomer, CreateReviewRequest reviewRequest) {
        //TODO: Vy - check if the customer has bought the product, the order must be completed
        Customer customer = findCustomerById(currentCustomer.getId());
        Product product = productService.findProductById(reviewRequest.getProductId());
        List<Order> orders = customer.getOrders();

        for (Order order : orders) {
            //TODO: not checking delivered order now
//            if (order.getStatus().equals(DELIVERED)) {
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getProduct().equals(product)) {
                    Review review = Review.builder()
                            .customer(customer)
                            .product(product)
                            .rating(reviewRequest.getRating())
                            .comment(reviewRequest.getComment())
                            .createdAt(LocalDateTime.now())
                            .build();

                    reviewService.save(review);
                    return ResponseEntity.ok(Response.builder().status(200).message("Create review successfully").data(null).build());
                }
            }
//            }
        }


        return ResponseEntity.ok(Response.builder()
                .status(400)
                .message("Failed to create review for product")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> updateReview(Customer currentCustomer, UpdateReviewRequest updateReviewRequest) {
        User customer = findCustomerById(currentCustomer.getId());
        List<Review> currentReview = reviewService.getReviewByCustomer(customer);
//        currentReview.setRating(updateReviewRequest.getRating());
//        currentReview.setComment(updateReviewRequest.getComment());
//        currentReview.setCreatedAt(LocalDateTime.now());
//        reviewService.save(currentReview);
        return ResponseEntity.ok(Response.builder().status(200).message("Update review successfully").data(null).build());
    }

    public ResponseEntity<Response> deleteReview(Long reviewId) {
        reviewService.deleteByReviewId(reviewId);

        return ResponseEntity.ok(Response.builder().status(200).message("Delete Review successfully").data(null).build());
    }

    public ResponseEntity<Response> getAllReview() {
        List<Review> reviews = reviewService.getAllReview();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all review successfully")
                .data(reviews)
                .build());
    }

    public ResponseEntity<Response> getReviewByProduct(Product product) {
        List<Review> reviews = reviewService.getReviewByProduct(product);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all review by product successfully")
                .data(reviews)
                .build());
    }

    public ResponseEntity<Response> getOrders(Long id) {
        Customer customer = findCustomerById(id);
        List<Order> orders = customer.getOrders();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all orders successfully")
                .data(orders)
                .build());
    }

    public ResponseEntity<Response> getOldOrders(Long id) {
        Customer customer = findCustomerById(id);
        List<Order> orders = customer.getOldOrders();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all old orders successfully")
                .data(orders)
                .build());
    }
}
