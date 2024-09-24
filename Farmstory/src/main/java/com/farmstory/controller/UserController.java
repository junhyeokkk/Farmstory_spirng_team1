package com.farmstory.controller;

import com.farmstory.service.EmailService;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(@PathVariable("type")  String type,
                                       @PathVariable("value") String value, HttpSession session, Model model)  {

        log.info("type : "+type+", value : "+value);
        int count = userService.selectCountUserByType(type,value);

        log.info("count : "+count);
        //해당되는 유저가 없는 email일때,
        if(type.equals("email") && count == 0){
            LocalDateTime requestedAt = LocalDateTime.now();
            String code = emailService.sendMail(value, "/contents/user/email.html",session);
            log.info("value code : "+code);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        log.info(resultMap.toString());

        return ResponseEntity.ok().body(resultMap);

    }

    //이메일 일치 검사
    @ResponseBody
    @PostMapping("/email")
    public ResponseEntity<?> checkUser(@RequestBody Map<String, String> jsonData
            , HttpSession session) {

        log.info("checkEmail code : " + jsonData);

        String receiveCode = jsonData.get("code");
        log.info("checkEmail receiveCode : " + receiveCode);


        String sessionCode = (String) session.getAttribute("code");


        log.info("sessionCode : "+sessionCode);


        // Json 생성
        Map<String, Object> resultMap;
        if(sessionCode.equals(receiveCode)){
            // Json 생성
            resultMap = new HashMap<>();
            resultMap.put("result", true);


        }else{
            // Json 생성
            resultMap = new HashMap<>();
            resultMap.put("result", false);
        }

        session.removeAttribute("code");

        return ResponseEntity.ok().body(resultMap);


    }
}
