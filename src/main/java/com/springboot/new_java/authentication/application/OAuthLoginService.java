package com.springboot.new_java.authentication.application;
import ch.qos.logback.classic.Logger;
import com.springboot.new_java.authentication.domain.AuthTokens;
import com.springboot.new_java.authentication.domain.AuthTokensGenerator;
import com.springboot.new_java.authentication.domain.oauth.*;
import com.springboot.new_java.data.repository.SnsRepository;
import com.springboot.new_java.data.entity.Sns;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final SnsRepository snsRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AuthController.class);

    public AuthTokens login(OAuthLoginParams params) {
        LOGGER.info("TET : {}",params);
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        LOGGER.info("AuthInfoResponse : {}",oAuthInfoResponse);
        Long snsId = findOrCreateSns(oAuthInfoResponse);
        LOGGER.info("snsId : {}",snsId);
        return authTokensGenerator.generate(snsId);
    }

    private Long findOrCreateSns(OAuthInfoResponse oAuthInfoResponse) {
        return (Long) snsRepository.findByEmail(oAuthInfoResponse.getEmail())

                .map(Sns::getId)
                .orElseGet(() -> newSns(oAuthInfoResponse));
    }

    private Long newSns(OAuthInfoResponse oAuthInfoResponse) {
        Sns sns = Sns.builder()
                 .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return snsRepository.save(sns).getId();
    }
}