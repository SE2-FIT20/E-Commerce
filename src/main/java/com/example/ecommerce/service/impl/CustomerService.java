package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.domain.Order.PaymentMethod;
import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.request.order.AddToCartRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.*;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.service.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.ecommerce.domain.Order.OrderStatus.*;
import static com.example.ecommerce.domain.Transaction.TransactionType.IN;
import static com.example.ecommerce.domain.Transaction.TransactionType.OUT;
import static com.example.ecommerce.dto.request.order.AddToCartRequest.OrderItemDTO;
import static com.example.ecommerce.utils.Utils.isValidCardNumber;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final OrderService orderService;
    private final DeliveryPartnerService deliveryPartnerService;
    private final StoreService storeService;
    private final ReviewService reviewService;
    private final NotificationService notificationService;
    private final PaymentInformationService paymentInformationService;
    private final PromotionRepository promotionRepository;
    private final MiniGamePlayingRecordService miniGamePlayingRecordService;
    private final TransactionService transactionService;
    private final PromotionService promotionService;
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public ResponseEntity<Response> getCustomerInformationById(Long customerId) {
        Customer customer = findCustomerById(customerId);

        CustomerDetailedInformation customerInformation = new CustomerDetailedInformation(customer);
        Response response = Response.builder()
                .status(200)
                .message("Get customer information successfully")
                .data(customerInformation)
                .build();

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
    public ResponseEntity<Response> checkout(Long customerId, CheckoutRequest request) {
        Customer customer = findCustomerById(customerId);
        Cart cart = customer.getCart();

        DeliveryPartner deliveryPartner = deliveryPartnerService
                .findDeliveryPartnerById(request.getDeliveryPartnerId());
        PaymentMethod paymentMethod = PaymentMethod.fromString(request.getPaymentMethod());

        // CHECK ONLINE PAYMENT TO SUBTRACT BALANCE
        if (paymentMethod.equals(PaymentMethod.ONLINE_PAYMENT)) {
            double totalAmount = cart.getTotalPrice();
            // check if customer has enough balance to checkout by online payment method
            if (customer.getBalance() < totalAmount) {
                throw new IllegalStateException("Not enough balance to checkout");
            }
            customer.setBalance(customer.getBalance() - totalAmount);

            createOutTransaction(customer, totalAmount);
        }


        // CREATE ORDER
        // items in the cart are grouped into group by store
        createOrdersByStore(request, customer, deliveryPartner);

        // if customer use promotion, set used to true
        if (!cart.getPromotions().isEmpty()) {

            boolean allPromotionAreApplicable = checkAllPromotionAreApplicable(cart);
            if (!allPromotionAreApplicable) {
                throw new IllegalStateException("There is a promotion that is not applicable");
            }
            cart.getPromotions().forEach(promotion -> {
                promotion.setUsed(true);
            });
            promotionRepository.saveAll(cart.getPromotions());
        }

        cart.setItems(new ArrayList<>()); // empty the cart of customer after checking out
        customerRepository.save(customer);
            return ResponseEntity.ok(
                    Response.builder()
                    .status(200)
                    .message("Checkout successfully")
                    .data(null)
                    .build());
    }

    private boolean checkAllPromotionAreApplicable(Cart cart) {
        List<Promotion> promotionList = cart.getPromotions();

        for (Promotion promotion : promotionList) {
            boolean isUsable = checkIfPromotionUsable(promotion, cart);
            if (!isUsable) {
                return false;
            }
        }

        return true;
    }

    private void createOutTransaction(Customer customer, double totalAmount) {
        String description = "You have paid " + totalAmount + " for your order";
        Transaction transaction = Transaction.builder()
                .user(customer)
                .amount(totalAmount)
                .description(description)
                .transactionType(OUT)
                .createdAt(LocalDateTime.now())
                .build();
        transactionService.save(transaction);
    }

    private void createOrdersByStore(CheckoutRequest request, Customer customer, DeliveryPartner deliveryPartner) {
        PaymentMethod paymentMethod = PaymentMethod.fromString(request.getPaymentMethod());
        for (CartStoreItem cartStoreItem : customer.getCart().getItems()) {
            Store store = storeService.findStoreById(cartStoreItem.getStore().getId());
            List<OrderItem> items = cartStoreItem.getItems();

            Order order = Order.builder()
                    .customer(customer)
                    .store(store)
                    .items(items)
                    .status(PENDING)
                    .paymentMethod(paymentMethod)
                    .createdAt(LocalDateTime.now())
                    .deliveryPartner(deliveryPartner)
                    .destinationAddress(request.getDestinationAddress())
                    .build();

            // order is the pwning side of the relationship,
            // and it has the customer field, save it to db will make it appear to the customer's order list
            orderService.save(order);

            subtractQuantityOfProducts(items);

            sendNotificationForStore(store, order);

        }
    }

    private void subtractQuantityOfProducts(List<OrderItem> items) {
        List<Long> productIds = items.stream().map(OrderItem::getProduct).map(ProductBriefInfo::getId).toList();
        List<Product> products = productService.findProductsByIds(productIds);

        for (Product product : products) {
            for (OrderItem item : items) {
                if (item.getProduct().getId().equals(product.getId())) {
                    product.setQuantity(product.getQuantity() - item.getQuantity());
                    break;
                }
            }
        }

        productService.saveAll(products);
    }

    private void sendNotificationForStore(Store store, Order order) {
        Notification notification = Notification.builder()
                .content("You have a new order from " + order.getCustomer().getName())
                .order(order)
                .createdAt(LocalDateTime.now())
                .type(Notification.NotificationType.NEW_ORDER)
                .isRead(false)
                .build();

        notificationService.sendNotificationToUser(store.getId(), notification);
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
        Customer customer = findCustomerById(currentCustomer.getId());
        Product product = productService.findProductById(reviewRequest.getProductId());

        boolean hasAlreadyReview = reviewService.existsByCustomerAndProduct(customer, product);

        if (hasAlreadyReview) {
            throw new IllegalStateException("You have already reviewed this product");
        }

        boolean hasBoughtThisProductSuccessfully = checkBoughtProductSuccessfully(customer, product);
        if (!hasBoughtThisProductSuccessfully) {
            throw new IllegalStateException("You need to buy this product before you can review it");
        }

        Review review = Review.builder()
                .customer(customer)
                .product(product)
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .images(reviewRequest.getImages())
                .createdAt(LocalDateTime.now())
                .build();
        reviewService.save(review);
        return ResponseEntity.ok(
                Response.builder()
                        .status(200)
                        .message("Create review successfully")
                        .data(null)
                        .build());


    }

    private boolean checkBoughtProductSuccessfully(Customer customer, Product product) {
        List<Order> orders = customer.getOrders();
        for (Order order : orders) {
            if (order.getStatus().equals(DELIVERED)) {
                List<OrderItem> orderItems = order.getItems();
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getProduct().equals(new ProductBriefInfo(product))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public ResponseEntity<Response> updateReview(Long customerId, UpdateReviewRequest updateReviewRequest, Long reviewId) {
        User customer = findCustomerById(customerId);
        Review currentReview = reviewService.findReviewById(reviewId);

        if (!currentReview.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalStateException("You are not the owner of this review");
        }

        if (updateReviewRequest.getRating() != null) {
            currentReview.setRating(updateReviewRequest.getRating());
            currentReview.setUpdatedAt(LocalDateTime.now());
        }
        if (updateReviewRequest.getImages() != null) {
            currentReview.setImages(updateReviewRequest.getImages());
            currentReview.setUpdatedAt(LocalDateTime.now());
        }

        if (updateReviewRequest.getComment() != null) {
            currentReview.setComment(updateReviewRequest.getComment());
            currentReview.setUpdatedAt(LocalDateTime.now());

        }


        reviewService.save(currentReview);
        return ResponseEntity.ok(Response.builder().status(200).message("Update review successfully").data(null).build());
    }



    public ResponseEntity<Response> getAllReview() {
        List<Review> reviews = reviewService.getAllReview();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all review successfully")
                .data(reviews)
                .build());
    }



    public ResponseEntity<Response> getOrders(Long id, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to) {
        Customer customer = findCustomerById(id);

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);
        Page<Order> page;
        if (status.equals("ALL")) {
            page = orderService.findAllByCustomerAndCreatedAtBetween(customer, from, to, pageable);
        } else {
            List<Order.OrderStatus> statuses = new ArrayList<>();
            // the status of "CANCELLED" is a combination of "CANCELLED_BY_STORE" and "CANCELLED_BY_CUSTOMER
            if (status.equals("CANCELLED")) {
                statuses.add(CANCELLED_BY_STORE);
                statuses.add(CANCELLED_BY_CUSTOMER);
            } else {
                Order.OrderStatus orderStatus = fromString(status.toUpperCase());
                statuses.add(orderStatus);
            }
            page = orderService.findAllByCustomerAndStatusInAndCreatedAtBetween(customer, statuses, from, to, pageable);
        }
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .size(page.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all orders successfully")
                .data(pageResponse)
                .build());
    }


    public ResponseEntity<Response> deleteReview(Long id, Long reviewId) {

        Review review = reviewService.findReviewById(reviewId);

        if (!review.getCustomer().getId().equals(id)) {
            throw new IllegalStateException("You are not the owner of this review");
        }

        reviewService.deleteByReviewId(reviewId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete review successfully")
                .data(null)
                .build()
        );

    }

    public ResponseEntity<Response> countOrders(Long id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Customer customer = findCustomerById(id);

        Map<String, Long> mapCount = orderService.countOrdersByCustomer(customer, fromDateTime, toDateTime);


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Count orders successfully")
                .data(mapCount)
                .build());

    }

    public ResponseEntity<Response> checkEligibleToReview(Long id, Long productId) {
        Product product = productService.findProductById(productId); // check product exist
        Customer customer = findCustomerById(id);

        List<Order> orders = customer.getOrders();

        boolean existInOrder = orders.stream()
                .filter(order -> order.getStatus().equals(DELIVERED))
                .anyMatch(order -> order.getItems()
                        .stream()
                        .anyMatch(orderItem -> orderItem.getProduct().getId().equals(productId))
                );

        Map<String, Boolean> map = new HashMap<>();
        map.put("eligible", existInOrder);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Check eligible to review successfully")
                .data(map)
                .build());
    }

    public ResponseEntity<Response> topUpBalance(Long customerId, TopUpBalanceRequest request) {
        Customer customer = findCustomerById(customerId);
        PaymentInformation paymentInformation = paymentInformationService.findPaymentInformationById(request.getPaymentInformationId());
        if (!customer.getPaymentInformation().contains(paymentInformation)) {
            throw new IllegalStateException("You are not the owner of this payment information");
        }
        if (request.getAmount() <= 0) {
            throw new IllegalStateException("Amount must be greater than 0");
        }

        createInTransaction(customer, request.getAmount());
        customer.setBalance(customer.getBalance() + request.getAmount());
        save(customer);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Top up balance successfully")
                .data(null)
                .build());
    }

    private void createInTransaction(Customer customer, Double amount) {
        String message = "Top up " + amount + " VND to your account";
        Transaction transaction = Transaction.builder()
                .user(customer)
                .amount(amount)
                .description(message)
                .transactionType(IN)
                .build();
        transactionService.save(transaction);
    }

    public ResponseEntity<Response> getPaymentInformation(Long id) {
        Customer customer = findCustomerById(id);
        List<PaymentInformation> paymentInformation = customer.getPaymentInformation();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get payment information successfully")
                .data(paymentInformation)
                .build());
    }

    public ResponseEntity<Response> addPaymentInformationRequest(Long id, AddPaymentInformationRequest request) {
        Customer customer = findCustomerById(id);
        boolean supportedBank = request.getCardNumber().matches("^[459][0-9]{15}|^[459][0-9]{18}"); // only accept VISA, MasterCard, Napas
        if (!supportedBank) {
            throw new IllegalStateException("This bank is not supported, only accept VISA, MasterCard, Napas");
        }


        boolean validCardNumber = isValidCardNumber(request.getCardNumber());
        if (!validCardNumber) {
            throw new IllegalStateException("Invalid card number");
        }
        PaymentInformation.CardType cardType = null;
        if (request.getCardNumber().startsWith("4")) {
            cardType = PaymentInformation.CardType.VISA;
        } else if (request.getCardNumber().startsWith("5")) {
            cardType = PaymentInformation.CardType.MASTERCARD;
        } else if (request.getCardNumber().startsWith("9")) {
            cardType = PaymentInformation.CardType.NAPAS;
        }
        PaymentInformation paymentInformation = PaymentInformation.builder()
                .customer(customer)
                .cardNumber(request.getCardNumber())
                .nameOnCard(request.getNameOnCard())
                .expirationDate(request.getExpirationDate())
                .cardType(cardType)
                .build();


        paymentInformationService.save(paymentInformation);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Add payment information successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> deletePaymentInformation(Long customerId, Long paymentInformationId) {
        Customer customer = findCustomerById(customerId);
        PaymentInformation paymentInformation = paymentInformationService.findPaymentInformationById(paymentInformationId);
        if (!customer.getPaymentInformation().contains(paymentInformation)) {
            throw new IllegalStateException("You are not the owner of this payment information");
        }

        // because the relationship is bidirectional, so we need to delete both side
        paymentInformationService.deleteByPaymentInformationId(paymentInformationId);
        customer.getPaymentInformation().remove(paymentInformation);
        save(customer);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete payment information successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> getVouchersAndCouponsToAddToCart(Long id) {
        Customer customer = findCustomerById(id);
        Cart cart = customer.getCart();
        List<Promotion> promotions = customer.getVouchersAndCoupons();

        List<Promotion> usable = new ArrayList<>();
        List<Promotion> unUsable = new ArrayList<>();
        for (Promotion promotion : promotions) {

            boolean isUsable = checkIfPromotionUsable(promotion, cart);
            if (isUsable) {
                usable.add(promotion);
            } else {
                unUsable.add(promotion);
            }
        }


        Map<String, List> map = new HashMap<>();
        map.put("usable", usable);
        map.put("unUsable", unUsable);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get vouchers and coupons to add to cart successfully")
                .data(map)
                .build());
    }

    private boolean checkIfPromotionUsable(Promotion promotion, Cart cart) {

        try {
            promotionService.checkIfPromotionAndThrowExceptionIfUsable(promotion);
            // because vouchers can be used for every product
            if (promotion instanceof Voucher) {
                return true;
            } else {
                Coupon coupon = (Coupon) promotion;
                boolean productOfStoreInCart = cart.getItems().stream()
                        .anyMatch(cartItem -> cartItem.getItems()
                                .stream()
                                .anyMatch(orderItem -> orderItem.getProduct().getStore().getId().equals(coupon.getStore().getId()
                                ))
                        );
                // because coupon of a store is only usable for products of that store
                return productOfStoreInCart;
            }


        } catch (Exception e) {
            return false;
        }
    }


    public ResponseEntity<Response> updateOrderRequest(Long customerId, UpdateOrderRequest request) {
        Customer customer = findCustomerById(customerId);
        Order order = orderService.findOrderById(request.getOrderId());


        if (!customer.getOrders().contains(order)) {
            throw new IllegalStateException("You are not the owner of this order");
        }

        orderService.updateOrder(request);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update order successfully")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> checkEligibleToPlayMiniGame(Long id) {
        Customer customer = findCustomerById(id); // check customer exist

        boolean alreadyPlayedToday = miniGamePlayingRecordService.checkIfCustomerSavedAVoucherToday(customer.getId());
        Map<String, Boolean> map = Map.of("eligible", !alreadyPlayedToday);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Check eligible to play mini game successfully")
                .data(map)
                .build());


    }

    public ResponseEntity<Response> getPromotionsInCart(User currentCustomer) {
        Customer customer = findCustomerById(currentCustomer.getId());
        Cart cart = customer.getCart();

        Map<String, Promotion> map = new HashMap<>();
        map.put("voucher", null);
        map.put("coupon", null);

        for (Promotion promotion : cart.getPromotions()) {
            if (promotion instanceof Voucher) {
                map.put("voucher", promotion);
            } else {
                map.put("coupon", promotion);
            }
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get promotions in cart successfully")
                .data(map)
                .build());

    }
}
