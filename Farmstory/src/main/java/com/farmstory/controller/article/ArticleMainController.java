package com.farmstory.controller.article;

import com.farmstory.service.ArticleServicce;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ArticleMainController {

    private ArticleServicce articleServicce;


}
