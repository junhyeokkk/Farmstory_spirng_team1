package com.farmstory.entity;

import com.farmstory.dto.CommentDTO;
import com.farmstory.dto.FileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="csArticle")
public class CsArticle {


    @Id
    private String csNo;
    private int inquiry;   //문의 분류
    private int cateNo=504; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;
    //제목
    @Column(columnDefinition = "LongText")
    private String content;
    //내용
    private String writer;  //작성자
    @CreationTimestamp
    private LocalDateTime createdate;   //작성일 now()
    private LocalDateTime updateDate;
    private String regIp;   //작성자ip
    private int file = 0;
    private int hit =0;


    @Builder.Default
    private boolean isCompleted=false;

    //추가필드
    @Transient
    private String nick;



    @OneToMany(mappedBy = "ano", cascade = CascadeType.REMOVE)
    private List<FileEntity> fileList;

    @OneToMany(mappedBy = "parent",cascade =CascadeType.REMOVE )
    private List<Comment> commentList;




}
