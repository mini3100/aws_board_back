package com.korit.board.config;

import com.korit.board.filter.JwtAuthenticationFilter;
import com.korit.board.security.PrincipalEntryPoint;
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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();            // WebMvcConfig의 CORS 설정을 적용
        http.csrf().disable();  // CSRF 보호 비활성화
        http.authorizeRequests()
                .antMatchers("/auth/**")    // /auth 로 시작하는 모든 요청
                .permitAll()                // 모두 허용하겠다.
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(principalEntryPoint);

//        super.configure(http);  // 부모가 가지고 있는 원래 설정
    }
}
