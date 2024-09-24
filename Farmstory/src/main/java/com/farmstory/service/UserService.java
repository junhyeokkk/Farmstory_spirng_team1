package com.farmstory.service;

import com.farmstory.dto.TermsDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.entity.Terms;
import com.farmstory.entity.User;
import com.farmstory.repository.Termsrepository;
import com.farmstory.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Termsrepository termsRepository;
    private final ModelMapper modelMapper;




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

}
