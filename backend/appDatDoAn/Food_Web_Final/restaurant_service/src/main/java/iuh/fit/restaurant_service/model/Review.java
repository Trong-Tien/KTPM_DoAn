package iuh.fit.restaurant_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private String id;

    private String userId;
    private String menuItemId;
    private int rating; // 1 - 5
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String adminReply; // nội dung phản hồi từ admin
    private LocalDateTime updatedAt;
    private String restaurantId;

}

