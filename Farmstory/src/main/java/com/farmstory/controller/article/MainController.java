package com.farmstory.controller.article;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    //main page
    @GetMapping(value = {"/","/index","/category"})
    public String index() {
        return "index";
    }


}
