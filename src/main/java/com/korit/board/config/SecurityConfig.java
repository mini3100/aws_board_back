package com.korit.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  // 기존의 security 설정 안 쓰고 이거 쓰겠다.
@Configuration      // IoC에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .permitAll();               // 모두 허용하겠다.

//        super.configure(http);  // 부모가 가지고 있는 원래 설정
    }
}
