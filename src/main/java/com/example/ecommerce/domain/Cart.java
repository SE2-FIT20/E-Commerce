package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CartStoreItem;
import com.example.ecommerce.dto.response.StoreBriefInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
//        boolean added = false;
//
//        // store already exists in the cart
//        for (int i = 0; i < stores.size(); i++) {
//            CartStoreItem cartStoreItem = stores.get(i);
//            if (cartStoreItem.getStore().getId().equals(productStore.getId())) {
//                List<OrderItem> items = cartStoreItem.getItems();
//                // check if the product already exists cart also
//                boolean alreadyExists = items.stream()
//                        .anyMatch(item -> item.getProduct().getId().equals(product.getId()));
//
//                // update the quantity if the product already exists
//                if (alreadyExists) {
//                    OrderItem oldItem = items.stream()
//                            .filter(item -> item.getProduct().getId().equals(product.getId()))
//                            .findFirst()
//                            .get();
//                    oldItem.setQuantity(oldItem.getQuantity() + quantity);
//
//                } else {
//                    // add the product to the cart if it doesn't exist
//                    OrderItem newItem = OrderItem.builder()
//                            .product(product)
//                            .quantity(quantity)
//                            .build();
//
//                    items.add(newItem);
//                }
//
//                added = true;
//            }
//            // move the store to the first in the list
//            stores.remove(i);
//            stores.add(0, cartStoreItem);
//        }
//
//        // store doesn't exist in the cart
//        if (!added) {
//            OrderItem newItem = OrderItem.builder()
//                    .product(product)
//                    .quantity(quantity)
//                    .build();
//            List<OrderItem> items = new ArrayList<OrderItem>();
//            items.add(newItem);
//
//            CartStoreItem store = CartStoreItem.builder()
//                    .id(productStore.getId())
//                    .store(productStore)
//                    .items(items)
//                    .build();
////            Store store = new Store()
//            // add the store to the first in the list
//            stores.add(0, store);
//        }
    }

    public void removeItem(Product product) {
        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (alreadyExists) {
            items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        } else {
            throw new IllegalStateException("Product doesn't exist in the cart");

        }
//        Store productStore = product.getStore();
//        boolean removed = false;
//        for (int i = 0; i < stores.size(); i++) {
//            CartStoreItem cartStoreItem = stores.get(i);
//
//            if (cartStoreItem.getStore().getId().equals(productStore.getId())) {
//                List<OrderItem> items = cartStoreItem.getItems();
//                // check if the product already exists cart also
//                boolean alreadyExists = items.stream()
//                        .anyMatch(item -> item.getProduct().getId().equals(product.getId()));
//                if (alreadyExists) {
//                    items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
//                    removed = true;
//                }
//            }
//        }
//
//        if (!removed) {
//            throw new IllegalStateException("Product doesn't exist in the cart");
//        }

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
