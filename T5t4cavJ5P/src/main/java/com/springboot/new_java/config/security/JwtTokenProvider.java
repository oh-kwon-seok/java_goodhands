package com.springboot.new_java.config.security;


import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor

public class JwtTokenProvider {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}") // 롬복이 아니라 bean의 Value임
    private String secretKey = "secretKey";
    private final long tokenValidMillisecond = 1000L * 60 * 60;


    @PostConstruct
    protected  void init(){
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        System.out.println(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);
        LOGGER.info("[init] Jwt Token Provider내 secretKey 초기화 완료");
    }
    public String createToken(String id, List<String> auth){
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims  claims = Jwts.claims().setSubject(id);
        claims.put("auth", auth);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료",token);
        return token;

    }
    public Authentication getAuthentication(String token){
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

    }

    private String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료,info : {}", info);
        return info;

    }
    public String resolveToken(HttpServletRequest request){
        LOGGER.info("[resolveToken] HTTp 헤더에서 Token값 추출");
        return request.getHeader("X-AUTH-TOKEN");

    }
    public boolean validateToken(String token){
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());


        } catch (SignatureException e){
            LOGGER.info("[validateToken]  SignatureException",e);
            throw new JwtException(ErrorMessage.WRONG_TYPE_TOKEN.getMsg());


        }catch (MalformedJwtException e){
            LOGGER.info("[validateToken]  MalformedJwtException",e);
            throw new JwtException(ErrorMessage.UNSUPPORTED_TOKEN.getMsg());

        }catch (ExpiredJwtException e){
            LOGGER.info("[validateToken]  ExpiredJwtException",e);
            throw new JwtException(ErrorMessage.EXPIRED_TOKEN.getMsg());

        }catch (IllegalArgumentException e){
            LOGGER.info("[validateToken]  IllegalArgumentException",e);
            throw new JwtException(ErrorMessage.UNKNOWN_ERROR.getMsg());

        }
    }


}
