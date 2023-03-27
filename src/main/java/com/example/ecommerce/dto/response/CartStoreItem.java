package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.response.StoreBriefInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartStoreItem {

    private StoreBriefInfo store;

    private List<OrderItem> items;

}