package com.springboot.java_eco.authentication.infra.naver;
import com.springboot.java_eco.authentication.domain.oauth.OAuthLoginParams;
import com.springboot.java_eco.authentication.domain.oauth.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String code;
    private String state;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("state", state);
        return body;
    }
    @Override
    public String toString() {
        return "NaverLoginParams{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                // 다른 필드들도 필요에 따라 추가
                '}';
    }
}