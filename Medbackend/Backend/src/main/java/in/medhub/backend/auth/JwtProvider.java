package in.medhub.backend.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	
	private final String SECRET_KEY="YF5X7FDSTVfla9evHHhJRBG23GIumwQtel0f6ieqh36";
	private final long EXPIRATION_TIME = 86400000;
	
	private SecretKey getSignKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(Authentication authentication) {
		String username=authentication.getName();
		Date now=new Date();
		Date expiryDate=new Date(now.getTime()+EXPIRATION_TIME);
		
		System.out.println("From generate token");
		
		return Jwts.builder()
			   .subject(username)
			   .issuedAt(now)
			   .expiration(expiryDate)
			   .signWith(getSignKey())
			   .compact();
		
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims=Jwts.parser()
						.verifyWith(getSignKey())
						.build()
						.parseSignedClaims(token)
						.getPayload();
		
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false; // Token is expired, tampered, or invalid
        }
    }
	
}
