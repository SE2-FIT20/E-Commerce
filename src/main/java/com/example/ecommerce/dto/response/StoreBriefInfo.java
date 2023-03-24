package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreBriefInfo {
    private Long id;
    private String name;
    private String avatar;
    public StoreBriefInfo(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.avatar = store.getAvatar();
    }
}
