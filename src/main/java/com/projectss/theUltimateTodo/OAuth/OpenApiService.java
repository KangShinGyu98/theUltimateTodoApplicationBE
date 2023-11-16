package com.projectss.theUltimateTodo.OAuth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenApiService {


    private final TokenService tokenService;

    @Value("${kakao.rest-key}")
    private String kakaoKey;


    @Value("${proxy.host}") // 프록시 호스트를 프로퍼티로 설정하여 외부에서 설정할 수 있도록 합니다.
    private String proxyHost;

    @Value("${proxy.port}") // 프록시 포트를 프로퍼티로 설정하여 외부에서 설정할 수 있도록 합니다.
    private int proxyPort;

    public String getToken(String code){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();

        body.add("grant_type","authorization_code");
        body.add("client_id", kakaoKey);
        body.add("redirect_uri","http://localhost:5173/openApi/kakao");
        body.add("code",code);



        // SimpleClientHttpRequestFactory 생성 및 프록시 설정
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        factory.setProxy(proxy);

        // RestTemplate에 팩토리 설정
        RestTemplate restTemplate = new RestTemplate(factory);

//        RestTemplate restTemplate = new RestTemplate();

        LoginResponseDto loginResponseDto = restTemplate.postForObject(
                "https://kauth.kakao.com/oauth/token",
                new HttpEntity<>(body,headers),
                LoginResponseDto.class);
        log.info(loginResponseDto.toString());

        String token = getUserInfo(loginResponseDto.access_token(),restTemplate);
        return token;
    }
    public String getUserInfo(String token,RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Authorization", "Bearer "+token);

        ResponseEntity<String> response =
                restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                        HttpMethod.GET,
                        new HttpEntity<>(null, headers),
                        String.class);

        String body = response.getBody();
        log.info(body.toString());
        String myToken = "";

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            String id = jsonNode.get("id").asText();
            String nickname = jsonNode.get("properties").get("nickname").asText();
            String profileImage = jsonNode.get("properties").get("profile_image").asText();
            String thumbnailImage = jsonNode.get("properties").get("thumbnail_image").asText();
            myToken = tokenService.generateToken(id,nickname,profileImage,thumbnailImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 쿠키 생성 및 설정
//        Cookie cookie = new Cookie("token", myToken);
//        cookie.setMaxAge(30 * 60); // 쿠키 만료 시간 (초 단위, 여기서는 30분)
//        cookie.setPath("/"); // 모든 경로에서 접근 가능하도록 설정

        return myToken;

    }
//
//
}