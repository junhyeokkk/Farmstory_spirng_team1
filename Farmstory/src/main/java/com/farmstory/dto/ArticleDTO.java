package com.farmstory.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {
    private int boardNo;   //board no  autoincrement
    private String group;   // group  = { croptalk, event, community}
    private String cate; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;  //제목
    private String content;  //내용
    private String writer;  //작성자
    private String date;   //작성일 now()
    private String regIp;   //작성자ip

    //default 0 or null
    private String file;
    private String hit;
    private String comNo;


}
