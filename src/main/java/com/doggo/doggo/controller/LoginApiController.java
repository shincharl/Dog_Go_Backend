package com.doggo.doggo.controller;

import com.doggo.doggo.Service.LoginService;
import com.doggo.doggo.dto.LoginRequestDto;
import com.doggo.doggo.dto.LoginResponseDto;
import com.doggo.doggo.dto.RegisterDTO;
import com.doggo.doggo.dto.RegisterResponseDTO;
import com.doggo.doggo.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://dog-go-frontend-roan.vercel.app", allowCredentials = "true") //리액트가 돌아가는 주소
public class LoginApiController {

    // 1. 서비스 객체 생성자 주입
    private final LoginService loginService;

    // 2. 서비스에 데이터 처리 요청 및 반환 (로그인)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpSession session){
        try {
            Member member = loginService.login(requestDto);
            session.setAttribute("loginMember", member);
            return ResponseEntity.ok((Map.of("email", member.getEmail(), "name", member.getName(), "role", member.getRole())));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 완료");
    }
    
    // 2. 서비스에 데이터 처리 요청 및 반환 (회원가입)
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto, HttpSession session){
        try {
            Member admin = loginService.signup(dto);
            session.setAttribute("loginMember", admin);
            return ResponseEntity.ok(Map.of("email", admin.getEmail(), "name", admin.getName()));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}
