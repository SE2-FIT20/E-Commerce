package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.domain.Order.OrderStatus;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.store.UpdateStoreRequest;
import com.example.ecommerce.dto.response.*;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.service.NotificationService;
import com.example.ecommerce.service.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.ecommerce.domain.Order.OrderStatus.*;

@Service
public class StoreService {

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private NotificationService notificationService;

    public void  save(Store store) {
        storeRepository.save(store);
    }

    public ResponseEntity<Response> getStoreInformationById(Long storeId) {
        Store store = findStoreById(storeId);

        StoreDetailedInfo storeInformation = new StoreDetailedInfo(store);
        Response response = Response.builder()
                .status(200)
                .message("Get Store information successfully")
                .data(storeInformation)
                .build();

        return ResponseEntity.ok(response);
    }

    public Store findStoreById(Long storeId) {
        return storeRepository
                .findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found for storeId: " + storeId));
    }

    public ResponseEntity<Response> createProduct(Long storeId, CreateProductRequest request) {

        Store store = findStoreById(storeId);
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .store(store)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .images(request.getImages())
                .createdAt(LocalDateTime.now())
                .build();

        productRepository.save(product); // save product to database, since product is the ownind sstoreIde, the store will have this product in the inventory
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create product successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> deleteProductById(Long storeId, Long productId) {
        Store store = findStoreById(storeId);
        List<Product> inventory = store.getInventory();

        boolean isExist = inventory.stream().anyMatch(product -> product.getId().equals(productId));
        if (!isExist) {
            throw new NotFoundException("Product not found for productId: " + productId);
        } else {
            inventory.removeIf(product -> product.getId().equals(productId));
        }
        productRepository.deleteById(productId); // delete product from database (product is the owning side
        storeRepository.save(store); // save store to database (store is the inverse side)

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete product successfully")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> getAllOrders(Long storeId, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to) {
        Store store = findStoreById(storeId);

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Direction.valueOf(sortBy.toUpperCase()), filter));
        Page<Order> page;
        if (status.equals("ALL")) {
            page = orderService.findAllByStoreAndCreatedAtBetween(store, from, to, pageable);
        } else {
            List<OrderStatus> statuses = new ArrayList<>();
            // the status of "CANCELLED" is a combination of "CANCELLED_BY_STORE" and "CANCELLED_BY_CUSTOMER
            if (status.equals("CANCELLED")) {
                statuses.add(CANCELLED_BY_STORE);
                statuses.add(CANCELLED_BY_CUSTOMER);
            } else {
                OrderStatus orderStatus = fromString(status.toUpperCase());
                statuses.add(orderStatus);
            }
            page = orderService.findAllByStoreAndStatusInAndCreatedAtBetween(store, statuses, from, to, pageable);
        }

        PageResponse pageResponse = PageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .pageNumber(page.getNumber())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all orders successfully")
                .data(pageResponse)
                .build()
        );
    }

    @Transactional
    public ResponseEntity<Response> updateOrder(Long storeId, UpdateOrderRequest request) {
        Store store = findStoreById(storeId);
        Order order = orderService.findOrderById(request.getOrderId());

        if (!order.getStore().getId().equals(store.getId())) {
            throw new IllegalStateException("Order does not belong to this store");
        }

        //TODO: check if the update status is valid or not!
        order.setStatus(request.getStatus());
        orderService.save(order);
        sendNotificationToNotifyNewOrderStatus(request, order);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update order successfully")
                .data(null)
                .build());
    }

    private void sendNotificationToNotifyNewOrderStatus(UpdateOrderRequest request, Order order) {
        String message = null;
        List<Long> recipientIds = new ArrayList<>();
        if (request.getStatus().equals(CANCELLED_BY_CUSTOMER)) {
            message = String.format("Your order %s has been cancelled by customer %s", order.getOrderCode(), order.getCustomer().getName());
            recipientIds = List.of(order.getStore().getId()); // store
        } else if (request.getStatus().equals(CANCELLED_BY_STORE)) {
            message = String.format("Your order %s has been cancelled by store %s", order.getOrderCode(), order.getStore().getName());
            recipientIds = List.of(order.getCustomer().getId()); // customer
        } else if (request.getStatus().equals(READY_FOR_DELIVERY)) {
            message = String.format("You have a new order %s is ready for delivery", order.getOrderCode());
            recipientIds = List.of(order.getDeliveryPartner().getId()); // delivery partner
        } else if (request.getStatus().equals(DELIVERED)) {
            message = String.format("Your order %s has been delivered successfully", order.getOrderCode());
            recipientIds = List.of(order.getCustomer().getId(), order.getStore().getId()); // customer and store
        }

        for (Long recipientId : recipientIds) {
            if (message == null) break; // if there is nothing to send
            prepareNotificationAndSendToUser(recipientId, message);
        }
    }


    private void prepareNotificationAndSendToUser(Long userId, String message) {
        Notification notification = Notification.builder()
                .type(Notification.NotificationType.ORDER_STATUS_CHANGED)
                .content(message)
                .createdAt(LocalDateTime.now())
                .build();

        notificationService.sendNotificationToUser(userId, notification);
    }


    public ResponseEntity<Response> updateProduct(Long storeId, UpdateProductRequest request) {
        Store store = findStoreById(storeId);
        List<Product> inventory = store.getInventory();

        boolean isExist = inventory.stream().anyMatch(product -> product.getId().equals(request.getProductId()));
        if (!isExist) {
            throw new NotFoundException("Product not found for productId: " + request.getProductId());
        }

        for (Product product : inventory) {
            if (product.getId().equals(request.getProductId())) {
                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setCategory(request.getCategory());
                product.setPrice(request.getPrice());
                product.setQuantity(request.getQuantity());
                product.setImages(request.getImages());
                productRepository.save(product);
            }
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update product successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> searchStore(String keyword, Integer page, Integer elementsPerPage) {
        Pageable pageable = PageRequest.of(page, elementsPerPage);
        Page<Store> pageStore = storeRepository.findByNameContainingIgnoreCase(keyword, pageable);

        PageResponse pageResponse = PageResponse.builder()
                .content(StoreDetailedInfo.from(pageStore.getContent()))
                .totalPages(pageStore.getTotalPages())
                .size(pageStore.getSize())
                .pageNumber(pageStore.getNumber())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Search store successfully")
                .data(pageResponse)
                .build());
    }

    public ResponseEntity<Response> getOrderById(Long id, Long orderId) {
        Store store = findStoreById(id);
        List<Order> orders = store.getOrders();

        boolean isExist = orders.stream().anyMatch(order -> order.getId().equals(orderId));
        if (!isExist) {
            throw new NotFoundException("Order not found for orderId: " + orderId);
        }

        Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().get();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(order)
                .build());
    }
//    public ResponseEntity<Response> getProductByStore(Integer pageNumber, Long storeId) {
//        Store store = findStoreById(storeId);
//        Page<Product> page = productService.getProductOfStore(pageNumber, store);
//        PageResponse pageResponse = PageResponse.builder()
//                .content(page.getContent())
//                .totalPages(page.getTotalPages())
//                .pageNumber(pageNumber)
//                .build();
//        return ResponseEntity.ok(Response.builder()
//                .status(200)
//                .message("Get all products successfully")
//                .data(pageResponse)
//                .build());
//    }

    public ResponseEntity<Response> getProductByStoreFilterByReview(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsFilterByReview = products.stream()
                .filter(product -> product.getReviews().size() > 0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsFilterByReview)
                .build());
    }

    public ResponseEntity<Response> getProductByStoreSortByPriceAsc(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsSortByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsSortByPrice)
                .build());
    }

    public ResponseEntity<Response> getProductByStoreSortByPriceDesc(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsSortByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsSortByPrice)
                .build());
    }

    public ResponseEntity<Response> getProductsByStatus(Long id, Integer pageNumber, Integer elementsPerPage, String status,String filter, String sortBy) {
        Store store = findStoreById(id);
        Page<Product> pageProduct;
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);

        if (status.toUpperCase().equals(Product.Status.AVAILABLE.name())){
             pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);
            pageProduct = productRepository.findAllByStoreAndQuantityGreaterThan(store, 0, pageable);
        } else if (status.toUpperCase().equals(Product.Status.SOLD_OUT.name())){
            pageProduct = productRepository.findAllByStoreAndQuantityEquals(store, 0, pageable);
        } else {
            pageProduct = productRepository.findAllByStore(store, pageable);
        }


        PageResponse pageResponse = PageResponse.builder()
                .content(pageProduct.getContent())
                .totalPages(pageProduct.getTotalPages())
                .pageNumber(pageNumber)
                .size(pageProduct.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(pageResponse)
                .build());
    }

    public ResponseEntity<Response> updateInformation(Long id, UpdateStoreRequest updateStoreRequest) {
        Store store = findStoreById(id);

        if (updateStoreRequest.getName() != null) store.setName(updateStoreRequest.getName());
        if (updateStoreRequest.getDescription() != null) store.setDescription(updateStoreRequest.getDescription());
        if (updateStoreRequest.getAddress() != null) store.setAddress(updateStoreRequest.getAddress());
        if (updateStoreRequest.getAvatar() != null) store.setAvatar(updateStoreRequest.getAvatar());
        if (updateStoreRequest.getPhoneNumber() != null) store.setPhoneNumber(updateStoreRequest.getPhoneNumber());
        if (updateStoreRequest.getCity() != null) store.setCity(updateStoreRequest.getCity());
        storeRepository.save(store);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update store successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> searchOrderByCode(Long storeId, String orderCode) {
//        Store store = findStoreById(id);
        Order order = orderService.findByOrderCode(orderCode);

        if (order == null) {
            throw new NotFoundException("Order not found for orderCode: " + orderCode);
        }

        if (!order.getStore().getId().equals(storeId)) {
            throw new IllegalStateException("Order doesn't belong to store");
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(order)
                .build());
    }

    public ResponseEntity<Response> searchOrderByCustomerName(Long id, String customerName, Integer pageNumber, Integer elementsPerPage) {
        Store store = findStoreById(id);

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Customer customer = new Customer();
        customer.setName(customerName);

        Order orderExample = Order.builder()
                .customer(customer)
                .store(store)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("customer.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Order> example = Example.of(orderExample, matcher);

        Page<Order> page = orderService.findAll(example, pageable);
        PageResponse pageResponse = PageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .pageNumber(pageNumber)
                .size(page.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(pageResponse)
                .build());
    }

    public ResponseEntity<Response> countOrders(Long id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Store store = findStoreById(id);

        Map<String, Long> mapCount = orderService.countOrdersByStore(store, fromDateTime, toDateTime);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(mapCount)
                .build());
    }



}
