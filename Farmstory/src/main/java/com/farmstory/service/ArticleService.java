package com.farmstory.service;

import com.farmstory.dao.ArticleRepository;
import com.farmstory.dao.CateRepository;
import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CateDTO;
import com.farmstory.entity.Article;
import com.farmstory.entity.Cate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {


    private final ArticleRepository articleRepository;
    private final CateRepository cateRepository;


    public void insertArticle(ArticleDTO articleDTO,int cateNo) {
        Article article = articleDTO.toEntity();
    }
    public ArticleDTO selectArticle(int articleNo) {
        Optional<Article> opt = articleRepository.findById(articleNo);

        if(opt.isPresent()) {
            ArticleDTO article = opt.get().toDTO();
            return article;
        }
        return null;
    }

    public List<ArticleDTO> selectCateArticle(int cateNo){
        List<Article> entitys = articleRepository.findByCate_CateNo(cateNo);

        List<ArticleDTO> articles = entitys.stream().map(article -> article.toDTO()).toList();

        return articles;


    }
    public List<ArticleDTO> selectArticles() { return null;}
    public void updateArticle(Article article,int cateNo) {}
    public void deleteArticle(int articleNo) {}



}
