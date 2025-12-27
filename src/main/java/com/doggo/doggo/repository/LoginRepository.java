package com.doggo.doggo.repository;

import com.doggo.doggo.dto.LoginResponseDto;
import com.doggo.doggo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Member, Long> {

    // 아이디 비밀번호 찾기
    @Query(value = "SELECT name FROM member WHERE email = :email and password =:password", nativeQuery = true)
    LoginResponseDto findByLoginId(@Param("email") String email, @Param("password") String password);
    
    // 이메일로 먼저 찾고, 비밀번호는 서비스단에서 확인
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

}
