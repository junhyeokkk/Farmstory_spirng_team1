package com.farmstory.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;
    private String rdate;
    private String pDesc;

    // 업로드 전용
    private List<MultipartFile> files;

    // product 이미지 파일 리스트
    private List<pDescImgFileDTO> pList_fNo;

    // 외래키 컬럼
    private String prodCateNo;
}
