package com.springboot.new_java.config.security;

import ch.qos.logback.classic.Logger;

import com.springboot.new_java.data.entity.User;
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
        String requestURI = servletRequest.getRequestURI();

        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        LOGGER.info("[doFilterInternal] requestURI : {}", requestURI);

        // 온보딩 관련 경로와 인증 불필요 경로는 필터 통과
        if (isExcludedPath(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");

        try{
            if(token != null && jwtTokenProvider.validateToken(token)) {

                // 토큰에서 사용자 정보 조회
                User user = jwtTokenProvider.getUserFromToken(token);

                if (user != null) {
                    // 온보딩 완료 여부 체크 (profile 컬럼 사용)
                    if (user.getProfile_completed() == null || !user.getProfile_completed()) {
                        LOGGER.warn("[doFilterInternal] 온보딩 미완료 사용자 접근 시도. userId: {}", user.getId());

                        // 온보딩 필요 응답
                        servletResponse.setContentType("application/json;charset=UTF-8");
                        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        servletResponse.getWriter().write("{\"success\":false,\"code\":\"ONBOARDING_REQUIRED\",\"msg\":\"온보딩이 필요합니다.\"}");
                        return;
                    }

                    // 인증 정보 설정
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");

                } else {
                    LOGGER.error("[doFilterInternal] 사용자 정보를 찾을 수 없습니다.");
                    servletResponse.setContentType("application/json;charset=UTF-8");
                    servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    servletResponse.getWriter().write("{\"success\":false,\"code\":\"USER_NOT_FOUND\",\"msg\":\"사용자를 찾을 수 없습니다.\"}");
                    return;
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);

        } catch(JwtException ex){
            String message = ex.getMessage();
            if(ErrorMessage.UNKNOWN_ERROR.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.UNKNOWN_ERROR);
                LOGGER.info("[unknown]");
            }
            //잘못된 타입의 토큰인 경우
            else if(ErrorMessage.WRONG_TYPE_TOKEN.getMsg().equals(message)) {
                setResponse(servletResponse, ErrorMessage.WRONG_TYPE_TOKEN);
                LOGGER.info("[WRONG]");
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
    }

    /**
     * 필터를 적용하지 않을 경로 체크
     */
    private boolean isExcludedPath(String requestURI) {
        // 인증 불필요 경로
        return requestURI.startsWith("/auth/") ||           // 카카오 로그인 콜백 등
                requestURI.startsWith("/api/onboarding/") || // 온보딩 API
                requestURI.startsWith("/public/") ||         // 공개 API
                requestURI.equals("/") ||
                requestURI.startsWith("/error");
    }

    private void setResponse(HttpServletResponse response, ErrorMessage errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getCode());
        response.getWriter().print(errorMessage.getMsg());
    }
}