package com.farmstory.entity;

import com.farmstory.dto.CateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {
    @Id
    private int boardNo;   //board no  autoincrement

    @ManyToOne
    @JoinColumn(name ="cateNo")
    private Cate cateNO;
    private String cate; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;  //제목
    private String content;  //내용
    private String writer;  //작성자

    @CreationTimestamp
    private LocalDateTime date;   //작성일 now()
    private String regIp;   //작성자ip

    //default 0 or null
    private String file;
    private String hit;
    private String comNo;


}
