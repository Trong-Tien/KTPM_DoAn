    package iuh.fit.user_service.security;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;

    public class JwtTokenUtil {
        private static final String SECRET_KEY = "thinh-secret-key"; // phải giống bên auth_service

        public static String getUsername(String token) {
            return getClaims(token).getSubject();
        }

        public static String getRole(String token) {
            return (String) getClaims(token).get("role");
        }

        public static Claims getClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }

        public static boolean validateToken(String token) {
            try {
                // check thêm thuật toán cho chắc chắn
                Claims claims = getClaims(token);
                return true;
            } catch (Exception e) {
                System.out.println("❌ JWT validation failed: " + e.getMessage());
                return false;
            }
        }
    }
