package com.farmstory.controller.article;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CateDTO;
import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.PageResponseDTO;
import com.farmstory.entity.Article;
import com.farmstory.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final PageService pageService;
    private final UserService userService;
    private final EmailService emailService;


    @GetMapping("/{cateGroup}/{cateName}")
    public String list(@PathVariable("cateGroup") String group,
                           @PathVariable("cateName") String cateName,
                           @RequestParam(value = "content", required = false) String content,
                           @RequestParam(value ="page", defaultValue = "0") int page,
                           PageRequestDTO pageRequestDTO,
                           Model model){
        log.info("pageReqestDTO"+pageRequestDTO);
        log.info("cateGroup "+group+"cateName "+cateName);
        CateDTO cate = categoryService.selectCategory(group,cateName);

        log.info(cate);
        int cateNo = cate.getCateNo();
        pageRequestDTO.setCateNo(cateNo);
        log.info("cate : "+cate);
        //300번대 = croptalk ,  500대 = community
        List<ArticleDTO> articles = null;
        if ((cateNo >= 300 && cateNo < 400) || (cateNo >= 500 && cateNo < 600)) {

            PageResponseDTO pageResponseDTO = articleService.getAllArticles(pageRequestDTO,cateNo);

            log.info("start : "+pageResponseDTO.getStart());
            log.info("end : " +pageResponseDTO.getEnd());

            log.info(pageResponseDTO.getDtoList());
            model.addAttribute("pageResponseDTO",pageResponseDTO);


        }
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        return "boardIndex";

    }

    @GetMapping("/{cateGroup}/{cateName}/{articleNo}")
    public String list(@PathVariable("cateGroup") String group,
                       @PathVariable("cateName") String cateName,
                       @PathVariable("articleNo") int articleNo,
                       @RequestParam(value = "content", required = false) String content,
                       Model model){

        CateDTO cate = categoryService.selectCategory(group,cateName);

       ArticleDTO articleDTO =  articleService.selectArticle(articleNo);
       model.addAttribute("cate", cate);
       model.addAttribute("content", content);
       model.addAttribute("article", articleDTO);
        return "boardIndex";
    }


    @PostMapping("/{cateNo}/writer")
    public String writer(@PathVariable("cateNo") int cateNo,ArticleDTO articleDTO, HttpServletRequest req){
        CateDTO cate = categoryService.selectCateNo(cateNo);
        articleDTO.setCateNo(cate.getCateNo());
        articleDTO.setRegIp(req.getRemoteAddr());

        ArticleDTO savedArticle= articleService.insertArticle(articleDTO,cateNo);

        if(savedArticle ==null){
            return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=write&success=300";
        }
        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=list";
    }









}