package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.SignInResultDto;
import com.springboot.new_java.data.dto.SignUpResultDto;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.user.UserDto;
import com.springboot.new_java.data.entity.User;

import com.springboot.new_java.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController


@RequestMapping("/user")
public class SignController {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService){
        this.signService = signService;
    }



    @GetMapping(value= "/select")
    public ResponseEntity<List<User>> getTotalUser(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{
        long currentTime = System.currentTimeMillis();

        List<User> selectedTotalUser = signService.getTotalUser(commonInfoSearchDto);

        LOGGER.info("[getTotalUser] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalUser);
    }
    @GetMapping(value= "/info_select")
    public ResponseEntity<List<User>> getUser(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<User> selectedTotalUser = signService.getUser(commonInfoSearchDto);

        LOGGER.info("[getTotalUser] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalUser);

    }




    @PostMapping(value= "/save",consumes = "application/json", produces = "application/json")
    public SignUpResultDto save(@RequestBody UserDto userDto) throws RuntimeException {
        long currentTime = System.currentTimeMillis();

        SignUpResultDto signUpResultDto = signService.save(userDto);

        return signUpResultDto;
    }

    @PostMapping(value= "/update",consumes = "application/json", produces = "application/json")
    public SignUpResultDto update(@RequestBody UserDto userDto) throws RuntimeException {
        long currentTime = System.currentTimeMillis();

        SignUpResultDto signUpResultDto = signService.update(userDto);

        return signUpResultDto;
    }


    @PostMapping(value= "/sign-in", consumes = "application/json", produces = "application/json")
    public SignInResultDto signIn(
            @RequestBody User user, HttpServletRequest request
    ) throws RuntimeException {

        LOGGER.info("[signIn] 로그인을 시도하고 있습니다.id: {}, pw: ****", user.getId());
        LOGGER.info("[IP정보] 로그인을 시도하고 있습니다.IP: {}", request.getRemoteAddr());

        SignInResultDto signInResultDto = signService.signIn(user.getId(), user.getPassword(),request.getRemoteAddr());
        if(signInResultDto.getCode() == 0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다.id: {}, token : {}",user.getId(), signInResultDto.getToken());
        }
        return signInResultDto;
    }




    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> delete(@RequestBody Map<String, List<String>> requestMap) throws Exception {
        List<String> id = requestMap.get("id");
        signService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @PostMapping(value= "/excel_upload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> excelUploadUser(@RequestBody Map<String, List<Map<String, Object>>> requestMap) throws Exception {
        List<Map<String, Object>> requestList = requestMap.get("data");
        LOGGER.info("LIST : {}",requestList);

        signService.excelUploadUser(requestList);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 등록되었습니다.");
    }



    @GetMapping(value= "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");

    }


    @ExceptionHandler(value= RuntimeException.class)
    public ResponseEntity<Map<String,String>>  ExceptionHandler(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "appication/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        LOGGER.error("ExceptionHandler 호출,{},{}", e.getCause(),e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("error type",httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message","에러 발생");

        return new ResponseEntity<>(map,responseHeaders,httpStatus);

    }

    public static void sendLogData(String useSe, String sysUser) {
        try {
            // URL 설정
            String urlString = "https://log.smart-factory.kr/apisvc/sendLogData.json";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // HTTP 메서드와 헤더 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Cookie", "WAF=f3d78604bf02bdb611efeb5cb9e5a8b2; elevisor_for_j2ee_uid=5p7q478x1z5cp");
            conn.setDoOutput(true); // OutputStream 사용을 허용

            // 고정 값
            String crtfcKey = "$5$API$ET4JqhTkYv.EycGF/nNmNksuRCU6R1bBnkT6gXADNj3";
            String conectIp = "115.68.194.157";
            String dataUsgqty = "0";

            // 현재 시간 생성 (YYYY-MM-DD HH:MM:SS.SSS)
            String logDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            // JSON 데이터 생성
            String jsonInputString = String.format(
                    "{\n" +
                            "  \"crtfcKey\" : \"%s\",\n" +
                            "  \"logDt\" : \"%s\",\n" +
                            "  \"useSe\" : \"%s\",\n" +
                            "  \"sysUser\" : \"%s\",\n" +
                            "  \"conectIp\" : \"%s\",\n" +
                            "  \"dataUsgqty\" : \"%s\"\n" +
                            "}", crtfcKey, logDt, useSe, sysUser, conectIp, dataUsgqty);

            // JSON 데이터를 요청 본문에 쓰기
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int code = conn.getResponseCode();
            System.out.println(jsonInputString);
            System.out.println("Response Code: " + code);

            // 응답 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            System.out.println("@@ " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
