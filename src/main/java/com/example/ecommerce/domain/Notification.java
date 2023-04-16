package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"user", "order"})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Column(columnDefinition="TEXT")

    private String content;

    @OneToOne
    private Order order;
    private boolean isRead;
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private User user;
    public enum NotificationType {
        NEW_ORDER,
        ORDER_STATUS_CHANGED

    }
}
