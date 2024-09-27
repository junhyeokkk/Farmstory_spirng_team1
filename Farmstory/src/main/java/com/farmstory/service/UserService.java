package com.farmstory.service;

import com.farmstory.dto.TermsDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.entity.Terms;
import com.farmstory.entity.User;
import com.farmstory.repository.Termsrepository;
import com.farmstory.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@Service
@RequiredArgsConstructor
@RequestMapping("/api/contents/user")
public class UserService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Termsrepository termsRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;
    private final JavaMailSender javaMailSender;
    private final HttpSession session;




    public String loginUser(String uid, String password) {
       String endcodedpassword = passwordEncoder.encode(password);

       Optional<User> opt= userRepository.findByUidAndPass(uid,endcodedpassword);

       if(opt.isPresent()) {
           User user = opt.get();
           return user.getUid();
       }

       return null;

    }

    public UserDTO insertUser(UserDTO userDTO) {
      String encodepass =  passwordEncoder.encode(userDTO.getPass());
      userDTO.setPass(encodepass);

      User user =  modelMapper.map(userDTO, User.class);

      User saveUser = userRepository.save(user);

      return modelMapper.map(saveUser, UserDTO.class);
    }
    public int selectCountUserByType(String type,String value){
        log.info("value : "+value);
        int count=0;
        if(type.equals("uid")){
            count = userRepository.countByUid(value);

        }else if(type.equals("nick")){
            count = userRepository.countByNick(value);

        }else if(type.equals("email")){
            count= userRepository.countByEmail(value);

        }else if(type.equals("hp")){
            count= userRepository.countByHp(value);
        }
        log.info("count : "+count);

        return count;

    }

    public void selectUser(){}
    public void selectUsers(){}
    public void updateUser(){}
    public void deleteUser(){}


//terms
    public TermsDTO selectTemrs(){

        List<Terms> termsList = termsRepository.findAll();
        return termsList.get(0).toDTO();


    }







    // 아이디 찾기 서비스 추가
    public void receiveCode(String name, String email) {

        // 1. 이름과 이메일로 DB에서 유저 검색
        Optional<User> user = userRepository.findByNameAndEmail(name, email);

        if (user.isEmpty()) {
            throw new RuntimeException("해당 이름과 이메일로 계정을 찾을 수 없습니다.");
        }

        // 2. 이메일 서비스에서 코드 생성 및 이메일 전송 (세션에 코드 저장)
        String verificationCode = emailService.sendMail(email, "contents/user/email", session);


        // 3. 인증번호를 세션에 저장
        session.setAttribute("code", verificationCode);  // 세션에 인증번호 저장
        session.setAttribute("userName", name);  // 세션에 사용자 이름 저장
        session.setAttribute("userEmail", email);  // 세션에 사용자 이메일 저장

        // 4. Entity -> DTO 변환
//        return modelMapper.map(user.get(), UserDTO.class);  // User 엔터티를 UserDTO로 변환 후 반환
    }



    // 인증번호 검증 및 아이디 반환
    public String verifyCodeForUid(String verificationCode, String name, String email) {
        // 1. 세션에서 저장된 인증번호 및 사용자 정보 가져오기
        String sessionCode = (String) session.getAttribute("code");  // 세션에 저장된 인증번호 가져오기
        String sessionName = (String) session.getAttribute("userName");
        String sessionEmail = (String) session.getAttribute("userEmail");

        // 2. 검증: 세션에 저장된 인증번호와 사용자 정보가 입력된 값과 일치하는지 확인
        if (sessionCode == null || !sessionCode.equals(verificationCode)
                || !sessionName.equals(name) || !sessionEmail.equals(email)) {
            throw new RuntimeException("인증번호가 일치하지 않거나 사용자 정보가 일치하지 않습니다.");
        }

        // 3. 검증 성공 후, 유저의 아이디 반환
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new RuntimeException("해당 이름과 이메일로 계정을 찾을 수 없습니다."));

        return user.getUid();  // 유저의 아이디 반환
    }





}
