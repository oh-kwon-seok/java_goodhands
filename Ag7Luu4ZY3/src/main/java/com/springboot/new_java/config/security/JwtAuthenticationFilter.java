package com.springboot.new_java.config.security;

import ch.qos.logback.classic.Logger;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");

        try{

            if(token != null && jwtTokenProvider.validateToken(token)) {

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
            }


            filterChain.doFilter(servletRequest,servletResponse);






        } catch(JwtException ex){
            String message = ex.getMessage();
            if(ErrorMessage.UNKNOWN_ERROR.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.UNKNOWN_ERROR);
                LOGGER.info("[unnown]");
            }
            //잘못된 타입의 토큰인 경우
            else if(ErrorMessage.WRONG_TYPE_TOKEN.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.WRONG_TYPE_TOKEN);
                LOGGER.info("[WRING]");
            }
            //토큰 만료된 경우
            else if(ErrorMessage.EXPIRED_TOKEN.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.EXPIRED_TOKEN);
                LOGGER.info("[EXPIRED]");
            }
            //지원되지 않는 토큰인 경우
            else if(ErrorMessage.UNSUPPORTED_TOKEN.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.UNSUPPORTED_TOKEN);
                LOGGER.info("[UNSUPPORTED_TOKEN]");
            }
            else {
                setResponse(servletResponse, ErrorMessage.ACCESS_DENIED);
                LOGGER.info("[ACCESS_DENIED]");
            }


        }




//        if(token != null && jwtTokenProvider.validateToken(token)){
//
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
//        }
//
//        filterChain.doFilter(servletRequest,servletResponse);
    }
    private void setResponse(HttpServletResponse response, ErrorMessage errorMessage) throws RuntimeException,IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getCode());
        response.getWriter().print(errorMessage.getMsg());


    }


}
