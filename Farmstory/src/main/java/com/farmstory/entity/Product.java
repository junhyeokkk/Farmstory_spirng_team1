package com.farmstory.entity;

import com.farmstory.dto.pDescImgFileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;

    @CreationTimestamp
    private LocalDate rdate;
    private String pDesc;

    // product 이미지 파일 리스트 <<지워도됨>>
    @OneToMany(mappedBy = "pfNo")
    private List<pDescImgFile> pList_fNo;

    // 외래키 컬럼
    private String prodCateNo;

}
