package com.example.ecommerce.dto.request;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UpdateAdminRequest {
    private String name;
    private String avatar;


}
