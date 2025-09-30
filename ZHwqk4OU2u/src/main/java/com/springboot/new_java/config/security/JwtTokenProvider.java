package com.springboot.new_java.config.security;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.service.CareHomeService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtTokenProvider.class);
    private static UserRepository userRepository ;




    public JwtTokenProvider(UserRepository userRepository) {

        this.userRepository = userRepository;


    }

    @Value("${springboot.jwt.secret}")
    private static String secretKey ;
    private final long tokenValidMillisecond = 1000L * 60 * 60;


    @PostConstruct
    protected void init(){
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        System.out.println(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);
        LOGGER.info("[init] Jwt Token Provider내 secretKey 초기화 완료");
    }

    // 기존 createToken 메서드 (List<String> 버전)
    public String createToken(String id, List<String> auth){
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("auth", auth);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 오버로드된 createToken 메서드 (String 버전) - 카카오 로그인용
    public String createToken(String id, String auth){
        LOGGER.info("[createToken] 토큰 생성 시작 (String auth)");
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("auth", auth);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료 (String auth)");
        return token;
    }

    public Authentication getAuthentication(String token){
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        User user = getUserFromToken(token);
        if (user == null) {
            LOGGER.error("[getAuthentication] 사용자를 찾을 수 없습니다");
            return null;
        }

        // User 엔티티에서 권한 정보 추출 (role 필드가 있다고 가정)
        // User 엔티티 구조에 맞게 수정 필요
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, User ID : {}", user.getId());
        return new UsernamePasswordAuthenticationToken(user, "", Collections.singletonList(authority));
    }

    private String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료,info : {}", info);
        return info;
    }

    // 토큰에서 subject만 추출하는 메서드
    public static String getSubjectFromToken(String token) {
        LOGGER.info("[getSubjectFromToken] 토큰에서 subject 추출");
        try {
            String subject = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            LOGGER.info("[getSubjectFromToken] subject 추출 완료: {}", subject);
            return subject;
        } catch (Exception e) {
            LOGGER.error("[getSubjectFromToken] 토큰에서 subject 추출 실패: {}", e.getMessage());
            return null;
        }
    }

    // 토큰에서 User 객체 조회하는 메서드
    public static User getUserFromToken(String token) {
        LOGGER.info("[getUserFromToken] 토큰에서 사용자 정보 조회");
        try {
            String subject = getSubjectFromToken(token);
            if (subject == null) {
                return null;
            }

            // 숫자면 카카오 ID, 문자면 일반 사용자 ID로 판단
            try {
                Long kakaoId = Long.parseLong(subject);
                LOGGER.info("[getUserFromToken] 카카오 ID로 사용자 조회: {}", kakaoId);
                return userRepository.findByKakaoId(kakaoId).orElse(null);
            } catch (NumberFormatException e) {
                LOGGER.info("[getUserFromToken] 일반 ID로 사용자 조회: {}", subject);
                return userRepository.findById(subject).orElse(null);
            }
        } catch (Exception e) {
            LOGGER.error("[getUserFromToken] 사용자 조회 실패: {}", e.getMessage());
            return null;
        }
    }

    public static String resolveToken(HttpServletRequest request){
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token){
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (SignatureException e){
            LOGGER.info("[validateToken] SignatureException", e);
            throw new JwtException(ErrorMessage.WRONG_TYPE_TOKEN.getMsg());

        } catch (MalformedJwtException e){
            LOGGER.info("[validateToken] MalformedJwtException", e);
            throw new JwtException(ErrorMessage.UNSUPPORTED_TOKEN.getMsg());

        } catch (ExpiredJwtException e){
            LOGGER.info("[validateToken] ExpiredJwtException", e);
            throw new JwtException(ErrorMessage.EXPIRED_TOKEN.getMsg());

        } catch (IllegalArgumentException e){
            LOGGER.info("[validateToken] IllegalArgumentException", e);
            throw new JwtException(ErrorMessage.UNKNOWN_ERROR.getMsg());
        }
    }
}