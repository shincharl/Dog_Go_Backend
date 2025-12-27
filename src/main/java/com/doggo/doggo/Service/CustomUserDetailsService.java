package com.doggo.doggo.Service;

import com.doggo.doggo.entity.Member;
import com.doggo.doggo.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = loginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        // 권한을 GrantedAuthority로 변환
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                member.getPassword(),
                authorities
        );
    }
}
