package com.farmstory.repository.impl;

import com.farmstory.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.farmstory.dto.PageRequestDTO;
import com.farmstory.repository.custom.ArticleRepositoryCustom;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Log4j2
@AllArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {


    private final JPAQueryFactory queryFactory;
    private final QArticle qarticle = QArticle.article;
    private final QUser quser  = QUser.user;
    private final QCate qcate = QCate.cate;
    private final QFileEntity qfileentity = QFileEntity.fileEntity;
    private final QComment qcomment = QComment.comment;


    @Override
    public Page<Tuple> selectArticleAllForList(PageRequestDTO pagerequestDTO, Pageable pageable,int cateNo) {


        List<Tuple> content = queryFactory
                .select(qarticle, quser.nick)
                .from(qarticle)
                .join(quser)
                .on(qarticle.writer.eq(quser.uid))
                .join(qcate)
                .on(qcate.cateNo.eq(qarticle.cateNo))
                .where(qcate.cateNo.eq(cateNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qarticle.articleNo.desc())
                .fetch();

        long total = queryFactory
                        .select(qarticle.count())
                        .from(qarticle)
                        .join(qcate)
                        .on(qcate.cateNo.eq(qarticle.cateNo))
                        .where(qcate.cateNo.eq(cateNo))
                        .fetchOne();

        log.info("total : "+total);





        return new PageImpl<Tuple>(content,pageable,total);

    }

    @Override
    public Page<Tuple> selectArticleForSearch(PageRequestDTO pagerequestDTO, Pageable pageable,int cateNo) {

        String type= pagerequestDTO.getType();
        String keyword= pagerequestDTO.getKeyword();
        //검색조건에 따라 where조건 표현식 생성
        BooleanExpression expression = null;

        if(type.equals("title")){
            expression = qarticle.title.like("%"+keyword+"%");
            log.info("expression : "+expression);
        }else if(type.equals("content")){
            expression = qarticle.content.like("%"+keyword+"%");
            log.info("expression : "+expression);

        }else if(type.equals("title_content")){
            expression = qarticle.title.like("%"+keyword+"%").or(qarticle.content.like("%"+keyword+"%"));
            log.info("expression : "+expression);

        }else if(type.equals("writer")){
            expression = quser.nick.like("%"+keyword+"%");
            log.info("expression : "+expression);

        }

        List<Tuple> content = queryFactory
                            .select(qarticle, quser.nick)
                            .from(qarticle)
                            .join(quser)
                            .on(qarticle.writer.eq(quser.uid))
                            .join(qcate)
                            .on(qcate.cateNo.eq(qarticle.cateNo))
                            .where(qcate.cateNo.eq(cateNo).and(expression))
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .orderBy(qarticle.articleNo.desc())
                            .fetch();

        long total = queryFactory
                .select(qarticle.count())
                .from(qarticle)
                .join(quser)
                .on(qarticle.writer.eq(quser.uid))
                .join(qcate)
                .on(qcate.cateNo.eq(qarticle.cateNo))
                .where(qcate.cateNo.eq(cateNo).and(expression))
                .fetchOne();



        log.info("total : "+total);
        return new PageImpl<Tuple>(content,pageable,total);
    }

    @Override
    public List<Tuple> selectArticleById(int no) {
        List<Tuple>  content = queryFactory
                                .select(qarticle,qfileentity,qcomment)
                                .from(qarticle)
                                .join(qfileentity)
                                .on(qfileentity.ano.eq(qarticle.articleNo))
                                .join(qcomment)
                                .on(qcomment.parent.eq(qarticle.articleNo))
                                .fetch();

        log.info("content : "+content);

        return content;
    }





}


