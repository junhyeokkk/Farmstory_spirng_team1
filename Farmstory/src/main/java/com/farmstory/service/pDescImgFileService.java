package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pDescImgFileDTO;
import com.farmstory.entity.pDescImgFile;
import com.farmstory.repository.pDescImgFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class pDescImgFileService {

    private final pDescImgFileRepository pDescImgFileRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public List<pDescImgFileDTO> uploadpDescImgFile(ProductDTO productDTO) {

        // 파일 업로드 경로 파일 객체 생성
        File fileUploadPath = new File(uploadPath+"/product");


        // 파일 업로드 경로가 존재하지 않으면 디렉터리 생성
        if(!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        // 파일 업로드 시스템 경로 구하기
        String path = fileUploadPath.getAbsolutePath();

        // 파일 정보 객체 가져오기 -> 이름 바꿀 필요 PList_fNo
        List<MultipartFile> files = productDTO.getFiles();

        // 첨부파일 정보 객체 리스트 생성
        List<pDescImgFileDTO> uploadFiles = new ArrayList<>();

        for (MultipartFile file : files) {

            if(!file.isEmpty()){
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    file.transferTo(new File(path, sName));
                } catch (IOException e) {
                    log.error(e);
                }
// p_No는 나중에???
                pDescImgFileDTO pDescImgFiledto = pDescImgFileDTO.builder()
                        .p_oName(oName)
                        .p_sName(sName)
                        .build();
                uploadFiles.add(pDescImgFiledto);
            }
        }
        return uploadFiles;
    }

    public void insertpDescImgFile(pDescImgFileDTO pDescImgFileDTO) {
        pDescImgFile DescImgFile =  modelMapper.map(pDescImgFileDTO, pDescImgFile.class);
        pDescImgFileRepository.save(DescImgFile);
    }


}
