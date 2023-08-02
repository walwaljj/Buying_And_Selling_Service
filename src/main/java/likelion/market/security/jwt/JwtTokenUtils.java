package likelion.market.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.cert.X509CertSelector;
import java.time.Instant;
import java.util.Date;

/**
 * Jwt 기능 클래스
 */
@Component
public class JwtTokenUtils {

    private final Key singleKey;
    private final JwtParser parser;

    public JwtTokenUtils(
            @Value("${jwt.secret}") String jwtSecret
    ) {
        this.singleKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.parser = Jwts.parserBuilder()
                .setSigningKey(this.singleKey)
                .build();
    }

    public String generateToken(UserDetails userDetails) {

        Claims claims = Jwts.claims()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(120)));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(singleKey)
                .compact();
    }

    /**
     * jwt 가 유효한지 판별합니다.
     *
     * @param token 토큰 정보
     * @return boolean  올바른 jwt == true , 이외 false
     */
    public boolean validate(String token) {

        try{
            parser.parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * jwt 를 해석하고 , 사용자 정보를 반환합니다.
     *
     * @param token    유효한 토큰
     * @return Claims
     */
    public Claims parseClaims(String token) {
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean isExpired(String token) {
        Date expiredDate = parseClaims(token).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }
}
