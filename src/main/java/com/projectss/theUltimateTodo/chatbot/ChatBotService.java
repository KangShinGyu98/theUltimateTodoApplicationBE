package com.projectss.theUltimateTodo.chatbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectss.theUltimateTodo.OAuth.User;
import com.projectss.theUltimateTodo.OAuth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatBotService {

    private final UserRepository userRepository;

    public String register(String body){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            // intent의 name 값 가져오기
            String intentName = jsonNode.path("intent").path("name").asText();
            // app_user_id 값 가져오기
            String profile = jsonNode.path("action").path("params").path("profile").asText();
            JsonNode profileNode = objectMapper.readTree(profile);
            String appUserId = profileNode.path("app_user_id").asText();
            log.info("profile : {}, appUserId : {}",profile,appUserId);
            // openId 값 가져오기
            String openId = jsonNode.path("userRequest").path("user").path("id").asText();

            log.info("intentName : {}, appUserId : {}, openId {}",intentName,appUserId,openId);

            // intent의 name이 "인증 블록"인지 확인
            if ("인증 블록".equals(intentName)) {
                if (userRepository.existsUserByOpenId(openId)){
                    log.info("이미 가입된 회원");
                    return "{\n" +
                            "    \"version\": \"2.0\",\n" +
                            "    \"template\": {\n" +
                            "        \"outputs\": [\n" +
                            "            {\n" +
                            "                \"simpleText\": {\n" +
                            "                    \"text\": \" 이미 가입된 회원입니다. 채팅창에 입력된 값은 메모장에 저장됩니다. \"\n" +
                            "                }\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}";
                }
                User user = userRepository.findById(appUserId).orElseThrow(()->new RuntimeException("처리 중 오류가 발생하였습니다."));
                user.setOpenId(openId);
                userRepository.save(user);
            }
            log.info("success logic");
            return "{\n" +
                    "    \"version\": \"2.0\",\n" +
                    "    \"template\": {\n" +
                    "        \"outputs\": [\n" +
                    "            {\n" +
                    "                \"simpleText\": {\n" +
                    "                    \"text\": \" 회원가입이 성공적으로 완료되었습니다. \n 앞으로 채팅창에 입력된 값은 메모장에 저장됩니다. \"\n" +
                    "                }\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            // 처리가 완료되면 응답을 반환

        } catch (IOException e) {
            log.info("catch 구문");
            e.printStackTrace();
            // 처리 중 오류가 발생한 경우 예외 처리
            throw new RuntimeException("처리 중 오류가 발생하였습니다.", e);
        }

    }

    public String fallback(String body){
        return "{\n" +
                "    \"version\": \"2.0\",\n" +
                "    \"template\": {\n" +
                "        \"outputs\": [\n" +
                "            {\n" +
                "                \"simpleText\": {\n" +
                "                    \"text\": \"메모에 저장되었습니다. POST.\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

    }
    private static String extractAppUserId(String text) {
        int colonIndex = text.indexOf("app_user_id\\\":");
        int braceIndex = text.indexOf("}");
        if (colonIndex != -1 && braceIndex != -1) {
            return text.substring(colonIndex + 1, braceIndex).trim();
        }
        return ""; // 추출 실패 시 null 반환 또는 예외 처리
    }
}
