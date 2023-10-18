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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 토큰에 email과 password가 담겨있음
        String email = authentication.getName();
        String password = (String) authentication.getCredentials(); // Object 다운캐스팅

        UserDetails principalUser = principalUserDetailsService.loadUserByUsername(email);

        // password : Client에서 가져온 것 - 암호화 안 된 것
        // principalUser.getPassword() : db에서 가져온 것 - 암호화 된 것
        if (!passwordEncoder.matches(password, principalUser.getPassword())) {
            throw new BadCredentialsException("BadCredentials");    // 비밀번호 불일치
        }

        return new UsernamePasswordAuthenticationToken(principalUser, password, principalUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
