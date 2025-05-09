package iuh.fit.restaurant_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ✅ Public endpoints (cho các service khác gọi)
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/*/exists").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/menu/*/exists").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/*/menu/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/{restaurantId}/menu/{menuItemId}/promotion").permitAll()
                        .requestMatchers("/api/restaurant/search").permitAll()
                        .requestMatchers("/api/restaurant/search-menu").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/*").permitAll()

                        // ✅ Manager endpoints
                        .requestMatchers("/api/manager/**").hasAuthority("ROLE_MANAGER")

                        // ✅ Admin endpoints
                        .requestMatchers("/api/admin/restaurant/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/restaurant/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/restaurant/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/restaurant/**").hasAuthority("ROLE_ADMIN")

                        // ✅ User-only review
                        .requestMatchers(HttpMethod.POST, "/api/restaurant/review").hasAuthority("ROLE_USER")

                        // ✅ GET access for USER and ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/restaurant/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // ✅ Other requests require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
