package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CartStoreItem;
import com.example.ecommerce.dto.response.StoreBriefInfo;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.Hibernate;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<OrderItem> items; // group the items by store

    public void addItem(Product product, Integer quantity) {

        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (alreadyExists) {
            for (OrderItem item : items) {
                if (item.getProduct().getId().equals(product.getId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                }
            }
        } else {
            OrderItem newItem = OrderItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .build();
            items.add(newItem);
        }

    }

    public void removeItem(Product product) {
        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (alreadyExists) {
            items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        } else {
            throw new IllegalStateException("Product doesn't exist in the cart");

        }
    }

    public List<CartStoreItem> getItems() {
        // group the items by store
        Map<StoreBriefInfo, List<OrderItem>> itemsByStore = new LinkedHashMap<>();
        for (OrderItem item : items) {
            StoreBriefInfo store = item.getProduct().getStore();
            if (itemsByStore.containsKey(store)) {
                itemsByStore.get(store).add(item);
            } else {
                List<OrderItem> storeItems = new ArrayList<>();
                storeItems.add(item);
                itemsByStore.put(store, storeItems);
            }
        }


        return itemsByStore.entrySet().stream()
                .map(entry -> CartStoreItem.builder()
                        .store(entry.getKey())
                        .items(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public List<OrderItem> getOrderItemsPreview() {
         Collections.reverse(items); // this will not be save to the database
        return items;
    }
}
