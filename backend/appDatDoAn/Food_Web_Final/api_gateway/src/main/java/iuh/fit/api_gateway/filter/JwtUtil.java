package iuh.fit.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // không cần hmacShaKeyFor
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("❌ JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return (String) getClaims(token).get("role");
    }
}
