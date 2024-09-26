package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pDescImgFile")
public class pDescImgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pfNo;
    private String p_oName;
    private String p_sName;
    private String rdate;

    // 외래키 컬럼
    private int pNo;
    private int imageType;
}
