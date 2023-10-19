package com.korit.board.security;


import com.korit.board.jwt.JwtProvider;
import com.korit.board.service.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalProvider implements AuthenticationProvider {

    private final PrincipalUserDetailsService principalUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // Authentication Manager를 직접 구현한 것.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {  // 예외가 생기면 throw 해준다.
        // UsernamePasswordAuthenticationToken을 Authentication으로 업캐스팅해서 들고 옴.

        // 토큰에 email과 password가 담겨있음
        String email = authentication.getName();
        String password = (String) authentication.getCredentials(); // Object 다운캐스팅

        UserDetails principalUser = principalUserDetailsService.loadUserByUsername(email);  // email로 PrincipalUser를 가져온다.
        // 찾지 못하면 UsernameNotFound 예외

        // password : Client에서 가져온 것 - 암호화 안 된 것
        // principalUser.getPassword() : db에서 가져온 것 - 암호화 된 것
        if (!passwordEncoder.matches(password, principalUser.getPassword())) {
            throw new BadCredentialsException("BadCredentials");    // 비밀번호 불일치
        }

        return new UsernamePasswordAuthenticationToken(principalUser, password, principalUser.getAuthorities());    // 유저에 대한 정보를 모두 담고 있음.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
