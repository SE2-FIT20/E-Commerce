package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CartStoreItem;
import com.example.ecommerce.dto.response.StoreBriefInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@ToString(exclude = {"items"}) // to prevent the error of "could not initialize proxy - no Session"
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    List<OrderItem> items; // group the items by store

    @OneToMany(cascade = CascadeType.ALL)
    private List<Promotion> promotions;
    
    public void addVoucher(Voucher voucher) {
        promotions.add(voucher);
        if (voucher != null) {
            Map<StoreBriefInfo, List<OrderItem>> itemsByStore = groupItemsByStore();
            // apply voucher to all items
            itemsByStore.forEach((store, storeItems) -> {
                storeItems.forEach(item -> {
                    item.applyPromotion(voucher);
                });
            });
        }

    }

    public void addCoupon(Coupon coupon) {
        promotions.add(coupon);
        if (coupon != null) {
            // apply coupon to the items of the store that the coupon belongs to
            Map<StoreBriefInfo, List<OrderItem>> itemsByStore = groupItemsByStore();

            itemsByStore.forEach((store, storeItems) -> {
                if (store.getId().equals(coupon.getStore().getId())) {
                    storeItems.forEach(item -> {
                        item.applyPromotion(coupon);
                    });
                }
            });
        }

    }


    public void removeVoucher(Voucher voucher) {
        if (voucher != null) {
            Map<StoreBriefInfo, List<OrderItem>> itemsByStore = groupItemsByStore();
            // apply voucher to all items
            itemsByStore.forEach((store, storeItems) -> {
                storeItems.forEach(item -> {
                    item.removePromotion(voucher);
                });
            });
        }
    }

    public void removeCoupon(Coupon coupon) {
        if (coupon != null) {
            // apply coupon to the items of the store that the coupon belongs to
            Map<StoreBriefInfo, List<OrderItem>> itemsByStore = groupItemsByStore();

            itemsByStore.forEach((store, storeItems) -> {
                if (store.getId().equals(coupon.getStore().getId())) {
                    storeItems.forEach(item -> {
                        item.removePromotion(coupon);
                    });
                }
            });
        }
    }

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

    @JsonIgnore
    public Double getTotalPrice() {
        return items.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    public List<CartStoreItem> getItems() {
        // group the items by store

        Map<StoreBriefInfo, List<OrderItem>> itemsByStore = groupItemsByStore();


        return itemsByStore.entrySet().stream()
                .map(entry -> CartStoreItem.builder()
                        .store(entry.getKey())
                        .items(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private Map<StoreBriefInfo, List<OrderItem>> groupItemsByStore() {
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
        return itemsByStore;
    }

    public List<OrderItem> getOrderItemsPreview() {
         Collections.reverse(items); // this will not be save to the database
        return items;
    }
}
