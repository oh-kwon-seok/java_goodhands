package com.springboot.java_eco.authentication.domain.oauth;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}