package com.springboot.new_java.authentication.application;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.authentication.domain.AuthTokens;
import com.springboot.new_java.authentication.infra.kakao.KakaoLoginParams;
import com.springboot.new_java.authentication.infra.naver.NaverLoginParams;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Controller
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;



    @Autowired
    public AuthController(OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value="/kakao", consumes="application/json", produces = "application/json")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) throws Exception{

        return ResponseEntity.ok(oAuthLoginService.login(params));
    }


//    @GetMapping(value="/naver")
//    public ResponseEntity<AuthTokens> loginNaver(@ModelAttribute NaverLoginParams params) throws Exception{
//        LOGGER.info("네이버로그인시도중 : {}",params);
//        return ResponseEntity.ok(oAuthLoginService.login(params));
//    }

    @GetMapping(value="/naver_login")
    public Map<String, String> getNaverLoginUrl() {
        String authUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&state=test";
        return Collections.singletonMap("authUrl", authUrl);
    }

    @GetMapping("/naver")
    @ResponseBody
    public  RedirectView naverCallback(@RequestParam("code") String code,
                                                @RequestParam("state") String state,
                                                NaverLoginParams params) {

        params.setCode(code);
        params.setState(state);

        boolean userLoggedIn = checkIfUserLoggedIn(params);
        RedirectView redirectView = new RedirectView();

        if (userLoggedIn) {
            String url = "https://jangan.godsun.co.kr/home";
            redirectView.setUrl(url);
            // 리다이렉션 수행
            return (RedirectView) redirectView;
        } else {
            String url = "https://jangan.godsun.co.kr/error";
            redirectView.setUrl(url);

            // 로그인이 안 된 경우에는 다른 응답 반환
            return (RedirectView) redirectView;
        }


    }

    private boolean checkIfUserLoggedIn(NaverLoginParams params) {
        // 실제 프로젝트에서는 세션 등을 통해 사용자 로그인 여부를 확인하는 로직을 구현
        // 여기에서는 가상의 조건으로 true를 반환하도록 함
        return true;
    }


}