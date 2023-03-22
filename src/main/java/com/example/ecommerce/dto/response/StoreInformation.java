package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreInformation {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String description;

    private String avatar;
    public StoreInformation(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.email = store.getEmail();
        this.address = store.getAddress();
        this.description = store.getDescription();
        this.avatar = store.getAvatar();
    }
}
