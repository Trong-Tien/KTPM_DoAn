package iuh.fit.restaurant_service.util;

import iuh.fit.restaurant_service.model.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class PromotionUtils {

    private PromotionUtils() {
        // Ngăn không cho khởi tạo lớp tiện ích
    }

    public static boolean isPromotionActive(Promotion promotion) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh")); // Giờ Việt Nam
        return (today.isEqual(promotion.getStartDate()) || today.isAfter(promotion.getStartDate())) &&
                (today.isEqual(promotion.getEndDate()) || today.isBefore(promotion.getEndDate()));
    }
}
