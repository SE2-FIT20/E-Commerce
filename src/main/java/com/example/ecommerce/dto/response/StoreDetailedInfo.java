package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
// for the data that can only be shown to the customer himself
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
    private LocalDateTime createdAt;
    private String phoneNumber;
    private double balance;
    public StoreDetailedInfo(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.email = store.getEmail();
        this.avatar = store.getAvatar();
        this.description = store.getDescription();
        this.address = store.getAddress();
        this.city = store.getCity();
        this.numberOfProducts = store.getInventory().size();
        this.createdAt = store.getCreatedAt();
        this.phoneNumber = store.getPhoneNumber();
        this.balance = store.getBalance();
        double rating = store.getInventory()
                .stream()
                .filter(product -> !product.getReviews().isEmpty())
                .mapToDouble(Product::getRating)
                .average()
                .orElse(0);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        this.averageProductRating = df.format(rating).equals("NaN") ? 0 : Double.parseDouble(df.format(rating));
    }


    public static List<StoreDetailedInfo> from(List<Store> stores) {
        return stores.stream().map(StoreDetailedInfo::new).toList();
    }

}
