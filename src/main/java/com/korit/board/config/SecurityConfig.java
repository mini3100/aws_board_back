package com.korit.board.config;

import com.korit.board.filter.JwtAuthenticationFilter;
import com.korit.board.security.PrincipalEntryPoint;
import com.korit.board.security.oauth2.OAuth2SuccessHandler;
import com.korit.board.service.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity  // 기존의 security 설정 안 쓰고 이거 쓰겠다.
@Configuration      // IoC에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalEntryPoint principalEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();            // WebMvcConfig의 CORS 설정을 적용
        http.csrf().disable();  // CSRF 보호 비활성화
        http.authorizeRequests()
                .antMatchers("/board/content", "/board/like/**")
                .authenticated()
                .antMatchers("/auth/**", "/board/**", "/boards/**")    // /auth 로 시작하는 모든 요청
                .permitAll()                // 모두 허용하겠다.
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(principalEntryPoint)  // filter에서 인증되지 않으면 principalEntryPoint에서 예외처리
                .and()
                .oauth2Login()  // 로그인 설정
                .loginPage("http://localhost:3000/auth/signin") // 로그인 페이지 경로
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint() // yml에서 인가, 토큰, 유저 정보 url 경로로 정보를 가져오는 과정을 여기서 수행한다.
                .userService(principalUserDetailsService);
    }
}
