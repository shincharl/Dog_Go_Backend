package com.doggo.doggo.Service;

import com.doggo.doggo.config.JwtUtil;
import com.doggo.doggo.dto.LoginRequestDto;
import com.doggo.doggo.dto.LoginResponseDto;
import com.doggo.doggo.dto.RegisterDTO;
import com.doggo.doggo.entity.Member;
import com.doggo.doggo.enums.Role;
import com.doggo.doggo.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // JWT 유틸

    private static final String ADMIN_CODE = "SECRET_ADMIN_CODE"; // 관리자 발급 코드

    // 로그인
    public LoginResponseDto login(LoginRequestDto dto){
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 실제 인증 수행
        Authentication authentication = authenticationManager.authenticate(authToken);

        // DB에서 회원 정보 조회
        Member member = loginRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(member.getEmail(), member.getRole().name());

        // DTO 반환
        return LoginResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole().name())
                .token(token)
                .build();
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

