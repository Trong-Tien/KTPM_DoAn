package iuh.fit.restaurant_service.mapper;

import iuh.fit.restaurant_service.dto.RestaurantDTO;
import iuh.fit.restaurant_service.model.Restaurant;

public class RestaurantMapper {

    public static RestaurantDTO toDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setEmail(restaurant.getEmail());
        dto.setPhone(restaurant.getPhone());
        dto.setDescription(restaurant.getDescription());
        dto.setImageUrl(restaurant.getImageUrl());
        dto.setManagerId(restaurant.getManagerId());
        dto.setOpeningHours(restaurant.getOpeningHours());
        dto.setCommissionRate(restaurant.getCommissionRate());
        if (restaurant.getLocation() != null) {
            dto.setLatitude(restaurant.getLocation().getY());
            dto.setLongitude(restaurant.getLocation().getX());
        }
        return dto;
    }

    public static Restaurant toEntity(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        if (dto.getId() != null && !dto.getId().isBlank()) {
            restaurant.setId(dto.getId()); // 👈 Giữ lại id nếu có
        }
        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setEmail(dto.getEmail());
        restaurant.setPhone(dto.getPhone());
        restaurant.setDescription(dto.getDescription());
        restaurant.setImageUrl(dto.getImageUrl());
        restaurant.setOpeningHours(dto.getOpeningHours());
        restaurant.setManagerId(dto.getManagerId());
        restaurant.setCommissionRate(dto.getCommissionRate());
        // Không set location ở đây (để làm riêng ở Service)
        return restaurant;
    }

}
