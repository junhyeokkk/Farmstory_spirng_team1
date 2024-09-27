package com.farmstory.entity;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleNo;   //board no  autoincrement

    private int cateNo;
    private String title;  //제목
    private String content;  //내용
    private String writer;  //작성자
    @CreationTimestamp
    private LocalDateTime date;   //작성일 now()
    private String regIp;   //작성자i
    //default 0 or null
    private int file;
    private int hit;
    private int com;

    //추가필드
    @Transient
    private String nick;




    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .articleNo(articleNo)
                .cateNo(cateNo)
                .title(title)
                .content(content)
                .writer(writer)
                .date(String.valueOf(date))
                .regIp(regIp)
                .file(file)
                .hit(hit)
                .com(com)
                .build();
    }

}