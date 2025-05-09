package iuh.fit.analytics1_service.controller;

import iuh.fit.analytics1_service.dto.*;
import iuh.fit.analytics1_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AnalyticsAdminController {

    private final AnalyticsService analyticsService;

    @GetMapping("/overview")
    public ResponseEntity<ManagerOverviewDTO> getOverviewForRestaurant(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(
                analyticsService.getOverviewForRestaurant(restaurantId, from, to, limit)
        );
    }



    @GetMapping("/top-restaurants")
    public ResponseEntity<List<TopRestaurantDTO>> getTopRestaurants(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(
                analyticsService.getTopRestaurants(from, to, limit)
        );
    }

    @GetMapping("/revenue-by-day")
    public ResponseEntity<List<DailyRevenueDTO>> getSystemRevenueByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(
                analyticsService.getSystemRevenueByDay(from, to)
        );
    }

    @GetMapping("/revenue-by-month")
    public ResponseEntity<List<PeriodRevenueDTO>> getRevenueByMonth(
            @RequestParam String from, @RequestParam String to
    ) {
        return ResponseEntity.ok(analyticsService.getSystemRevenueByMonth(from, to));
    }

    @GetMapping("/revenue-by-year")
    public ResponseEntity<List<PeriodRevenueDTO>> getRevenueByYear(
            @RequestParam String from, @RequestParam String to
    ) {
        return ResponseEntity.ok(analyticsService.getSystemRevenueByYear(from, to));
    }


}
