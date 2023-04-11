package com.example.ecommerce.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class MarkNotificationsAsRead {
    private List<Long> ids;
}
