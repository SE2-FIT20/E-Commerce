package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StoreDetailedInfo {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private String address;

    public StoreDetailedInfo(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.avatar = store.getAvatar();
        this.description = store.getDescription();
        this.address = store.getAddress();
    }


    public static List<StoreDetailedInfo> from(List<Store> stores) {
        return stores.stream().map(StoreDetailedInfo::new).toList();
    }

}
