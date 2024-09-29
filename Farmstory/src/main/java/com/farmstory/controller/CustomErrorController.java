package com.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // 에러 코드에 따라 다른 페이지로 리다이렉트
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                return "error/404"; // 404 에러 시
            } else if (statusCode == 500) {
                return "error/500"; // 500 에러 시
            }
        }


        return "redirect:/category/user/login"; // 그 외 모든 에러 시
    }


}

