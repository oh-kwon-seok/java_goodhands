package com.springboot.java_eco.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.data.dto.SignInResultDto;
import com.springboot.java_eco.data.dto.SignUpResultDto;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.user.UserDto;
import com.springboot.java_eco.data.entity.User;
import com.springboot.java_eco.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
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


}
