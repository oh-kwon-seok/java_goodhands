package com.springboot.new_java.controller;


import ch.qos.logback.classic.Logger;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.stockInout.StockInoutDto;
import com.springboot.new_java.data.entity.StockInout;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.service.StockInoutService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/stock_inout")
@Controller
public class StockInoutController {
    private final StockInoutService stockInoutService;
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(StockInoutController.class);

    @Autowired
    public StockInoutController(StockInoutService stockInoutService){
        this.stockInoutService = stockInoutService;
    }

    @GetMapping(value= "/info_select")
    public ResponseEntity<List<StockInout>> getStockInout(@ModelAttribute CommonInfoSearchDto commonInfoSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInout> selectedStockInout = stockInoutService.getStockInout(commonInfoSearchDto);

        LOGGER.info("[getTotalStockInoutSub] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedStockInout);

    }


    
    @GetMapping(value= "/select")
    public ResponseEntity<List<StockInout>> getTotalStockInout(@ModelAttribute CommonSearchDto commonSearchDto) throws RuntimeException{

        long currentTime = System.currentTimeMillis();

        List<StockInout> selectedTotalStockInout = stockInoutService.getTotalStockInout(commonSearchDto);

        LOGGER.info("[getTotalStockInout] response Time: {}ms,{}", System.currentTimeMillis() - currentTime);

        return ResponseEntity.status(HttpStatus.OK).body(selectedTotalStockInout);

    }



    @PostMapping(value= "/save", consumes = "application/json", produces = "application/json")
    public CommonResultDto createStockInout(@RequestBody StockInoutDto stockInoutDto) throws Exception{
        long currentTime = System.currentTimeMillis();
        LOGGER.info("[stockInoutDto]  : {}", stockInoutDto);

        User user = new User();
        sendLogData("DO6007",stockInoutDto.getUser_id());

        CommonResultDto stockInoutResultDto = stockInoutService.saveStockInout(stockInoutDto);
        LOGGER.info("[createStockInout] response Time : {}ms", System.currentTimeMillis() - currentTime);
        return  stockInoutResultDto;

    }
    
    @PostMapping(value= "/update", consumes = "application/json", produces = "application/json")
    public CommonResultDto updateStockInout(@RequestBody StockInoutDto stockInoutDto)
            throws Exception{
        CommonResultDto stockInoutResultDto = stockInoutService.updateStockInout(stockInoutDto);
        return stockInoutResultDto;
    }
    @PostMapping(value= "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteFactory(@RequestBody Map<String, List<Long>> requestMap) throws Exception {
        List<Long> uid = requestMap.get("uid");
        stockInoutService.deleteStockInout(uid);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
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
