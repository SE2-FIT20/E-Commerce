package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.*;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserInformation {
    private Long id;
    private String name;
    private String email;
    private String avatar;
    private boolean isLocked;
    private String role;
    private LocalDateTime createdAt;
    private Object additionalData; // this can be used to store additional data based on the role

    public UserInformation(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.isLocked = user.isLocked();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        initAdditionData(user);
    }

    private void initAdditionData(User user) {
        // if user is customer, return address, phone number, number of orders
        // if user is store, return number of products in inventory, number of products sold
        // if user is delivery partner, return number of orders delivered
        Map<String, Object> data = new LinkedHashMap<>();
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            data.put("addresses", customer.getAddresses());
            data.put("phoneNumber", customer.getPhoneNumber());
            data.put("numberOfOrders", customer.getOrders().size());
        } else if (user instanceof Store) {
            Store store = (Store) user;
            data.put("numberOfProductsInInventory", store.getInventory().size());

            int numberOfProductsSold = store.getInventory()
                    .stream()
                    .mapToInt(Product::getSold)
                    .sum();
            data.put("numberOfProductsSold", numberOfProductsSold);
            data.put("city", store.getCity());
            data.put("phoneNumber", store.getPhoneNumber());

        } else if (user instanceof DeliveryPartner) {
            DeliveryPartner deliveryPartner = (DeliveryPartner) user;

            long numberOfOrdersDelivered = deliveryPartner.getOrders()
                    .stream().filter(order -> order.getStatus().equals(Order.OrderStatus.DELIVERED))
                    .count();
            data.put("numberOfOrdersDelivered", numberOfOrdersDelivered);
        }

        if (!data.isEmpty()) {
            this.additionalData = data;
        }
    }

    public static List<UserInformation> from(List<User> users) {
        return users.stream().map(UserInformation::new).toList();
    }
}
