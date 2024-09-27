package com.farmstory.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class pDescImgFileDTO {
    private int pfNo;
    private String imageType;
    private String p_oName;
    private String p_sName;
    private String rdate;

    // 외래키 컬럼
    private int pNo;

}
