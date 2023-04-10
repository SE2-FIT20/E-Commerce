package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.User;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInformation {
    private Long id;
    private String name;
    private String email;
    private String avatar;
    private boolean isLocked;
    private String role;
    private LocalDateTime createdAt;

    public UserInformation(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.isLocked = user.isLocked();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }

    public static List<UserInformation> from(List<User> users) {
        return users.stream().map(UserInformation::new).toList();
    }
}
