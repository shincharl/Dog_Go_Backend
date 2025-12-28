package com.doggo.doggo.Service;

import com.doggo.doggo.dto.LoginRequestDto;
import com.doggo.doggo.dto.LoginResponseDto;
import com.doggo.doggo.dto.RegisterDTO;
import com.doggo.doggo.entity.Member;
import com.doggo.doggo.enums.Role;
import com.doggo.doggo.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    // 리파지토리 생성자 주입 실행
    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    private static final String ADMIN_CODE = "SECRET_ADMIN_CODE"; // 관리자 발급 코드

    // 로그인
    public Member login(LoginRequestDto dto){
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 실제 인증 수행
        Authentication authentication = authenticationManager.authenticate(authToken);

        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        // 회원 정보 반환
        return loginRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));
    }

    // 로그아웃
    public void logout() {
        session.invalidate();
    }

    // 회원가입
    public Member signup(RegisterDTO registerDTO){
       if (!ADMIN_CODE.equals(registerDTO.getAdminCode())){
           throw new RuntimeException("관리자 코드가 틀렸습니다.");
       }

       if(loginRepository.existsByEmail(registerDTO.getEmail())){
           throw new RuntimeException("이미 가입된 이메일입니다.");
       }

       Member admin = Member.builder()
               .name(registerDTO.getName())
               .email(registerDTO.getEmail())
               .password(passwordEncoder.encode(registerDTO.getPassword()))
               .role(Role.ROLE_ADMIN)
               .build();

       return loginRepository.save(admin);
    }
}
