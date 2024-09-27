package com.farmstory.repository;

import com.farmstory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

   // 이름과 이메일로 유저 검색
   public Optional<User> findByNameAndEmail(String name, String email);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByUidAndPass(String uid, String pass);

   public int countByUid(String uid);
   public int countByNick(String nick);
   public int countByEmail(String email);
   public int countByHp(String hp);

}
