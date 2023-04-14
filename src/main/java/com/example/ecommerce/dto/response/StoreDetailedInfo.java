package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
//TODO: considering remove this dto
public class StoreDetailedInfo {
    private Long id;
    private String email;
    private String name;
    private String avatar;
    private String description;
    private String address;
    private String city;
    private int numberOfProducts;
    private double averageProductRating;

    public StoreDetailedInfo(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.email = store.getEmail();
        this.avatar = store.getAvatar();
        this.description = store.getDescription();
        this.address = store.getAddress();
        this.city = store.getCity();
        this.numberOfProducts = store.getInventory().size();
        this.averageProductRating = store.getInventory()
                .stream()
                .mapToDouble(Product::getRating)
                .average()
                .orElse(0);
    }


    public static List<StoreDetailedInfo> from(List<Store> stores) {
        return stores.stream().map(StoreDetailedInfo::new).toList();
    }

}
