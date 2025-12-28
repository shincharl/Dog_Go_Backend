package com.doggo.doggo.controller;

import com.doggo.doggo.Service.LoginService;
import com.doggo.doggo.dto.LoginRequestDto;
import com.doggo.doggo.dto.LoginResponseDto;
import com.doggo.doggo.dto.RegisterDTO;
import com.doggo.doggo.dto.RegisterResponseDTO;
import com.doggo.doggo.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://dog-go-frontend-roan.vercel.app", allowCredentials = "true") //리액트가 돌아가는 주소
public class LoginApiController {

    // 1. 서비스 객체 생성자 주입
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    // 2. 서비스에 데이터 처리 요청 및 반환 (로그인)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response){
        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    requestDto.getEmail(),
                                    requestDto.getPassword()
                            )
                    );

            // Spring Security가 인식하는 방식으로 저장
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);

            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.ok(Map.of(
                    "email", authentication.getName(),
                    "role", authentication.getAuthorities()
            ));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("이메일 또는 비밀번호 오류");
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
