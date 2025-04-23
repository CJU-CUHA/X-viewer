package org.example.xviewer.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil{
    private SecretKey secretKey;  // JWT 서명에 사용할 비밀 키

    // 생성자에서 secret 키를 받아 SecretKey 객체를 초기화
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        // 주입된 secret 값을 기반으로 HMAC-SHA 알고리즘에 맞는 서명용 키를 생성
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createJWT(String username, String id, Long expiredMs) {
        return Jwts.builder()
                .setSubject(username)
                .claim("username", id) // 주석 해제
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey) // 누락된 서명
                .compact();
    }

    // JWT에서 username을 추출하는 메서드
    public String getUsername(HttpServletRequest request) {
        String token = resolveToken(request);
        // 토큰을 파싱하여 Claims 객체를 얻은 후, subject에서 username을 추출
        Claims claims = Jwts.parserBuilder()  // JWT 파싱을 위한 빌더 생성
                .setSigningKey(secretKey)  // 서명 검증을 위한 비밀 키 설정
                .build()  // 빌더 완료
                .parseClaimsJws(token)  // 토큰을 파싱하여 JWT Claims 객체로 변환
                .getBody();  // Claims의 payload 부분을 가져옴

        return claims.getSubject();  // JWT의 subject에서 username을 반환
    }

    // JWT에서 role을 추출하는 메서드
    public String getRole(String token) {
        // JWT 파싱 후 role 클레임을 추출하여 반환
        Claims claims = Jwts.parserBuilder()  // JWT 파싱을 위한 빌더 생성
                .setSigningKey(secretKey)  // 서명 검증을 위한 비밀 키 설정
                .build()  // 빌더 완료
                .parseClaimsJws(token)  // 토큰을 파싱하여 JWT Claims 객체로 변환
                .getBody();  // Claims의 payload 부분을 가져옴

        return claims.get("role").toString();  // role 클레임을 반환
    }

    // JWT가 만료되었는지 확인하는 메서드
    public boolean isExpired(String token) {
        // 토큰을 파싱하여 Claims 객체를 얻은 후, 만료 시간을 추출하여 현재 시간과 비교
        Claims claims = Jwts.parserBuilder()  // JWT 파싱을 위한 빌더 생성
                .setSigningKey(secretKey)  // 서명 검증을 위한 비밀 키 설정
                .build()  // 빌더 완료
                .parseClaimsJws(token)  // 토큰을 파싱하여 JWT Claims 객체로 변환
                .getBody();  // Claims의 payload 부분을 가져옴

        Date expiration = claims.getExpiration();  // 만료 시간 추출
        return expiration.before(new Date());  // 만료 시간이 현재 시간보다 이전이면 만료됨
    }


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // 수정된 부분
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

}
