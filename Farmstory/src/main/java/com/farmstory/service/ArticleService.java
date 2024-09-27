package com.farmstory.service;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.PageResponseDTO;
import com.farmstory.repository.ArticleRepository;
import com.farmstory.repository.CateRepository;
import com.farmstory.dto.ArticleDTO;
import com.farmstory.entity.Article;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {


    private final ArticleRepository articleRepository;
    private final CateRepository cateRepository;
    private final ModelMapper modelMapper;


    public ArticleDTO insertArticle(ArticleDTO articleDTO,int cateNo) {
       Article article = modelMapper.map(articleDTO, Article.class);
       Article savedArticle = articleRepository.save(article);

       return modelMapper.map(savedArticle, ArticleDTO.class);
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
        List<Article> entitys = articleRepository.findByCateNo(cateNo);

        List<ArticleDTO> articles = entitys.stream().map(article -> article.toDTO()).toList();

        return articles;


    }
    public List<ArticleDTO> selectArticles() { return null;}
    public void updateArticle(Article article,int cateNo) {}
    public void deleteArticle(int articleNo) {}


    public PageResponseDTO getAllArticles(PageRequestDTO pageRequestDTO,int cateNo) {

        Pageable pageable = pageRequestDTO.getPageable("articleNo",10);
        Page<Tuple> pageArticle =null;
        if(pageRequestDTO.getKeyword()==null) {
            pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable, cateNo);
        }else{
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable,cateNo);

        }

        List<ArticleDTO> articleList = pageArticle.stream().map(tuple ->{
                    Article article = tuple.get(0,Article.class);
                    String nick=tuple.get(1,String.class);
                    article.setNick(nick);
                    return modelMapper.map(article, ArticleDTO.class);
                }
        ).toList();


        int total = (int)pageArticle.getTotalElements();

        log.info("total : "+total);
        return PageResponseDTO.builder()
                .dtoList(articleList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }


}
