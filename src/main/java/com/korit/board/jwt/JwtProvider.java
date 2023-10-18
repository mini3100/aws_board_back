package com.korit.board.jwt;

import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;
    private final UserMapper userMapper;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.userMapper = userMapper;
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        Date date = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        return Jwts.builder()
                .setSubject("AccessToken")
                .setExpiration(date)
                .claim("email", email)
                .claim("disabled", principalUser.isEnabled())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
        return claims;
    }

    public String getToken(String bearerToken) {
        if(!StringUtils.hasText(bearerToken)) {
            return null;
        }
        return bearerToken.substring("Bearer ".length());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if(claims == null) {
            return null;
        }

        // 클레임 정보에서 이메일 값을 추출하여 해당 이메일을 가진 사용자를 데이터베이스에서 찾음
        User user = userMapper.findUserByEmail(claims.get("email").toString());
        if(user == null) {
            return null;
        }

        PrincipalUser principalUser = new PrincipalUser(user);

        // Spring Security의 UsernamePasswordAuthenticationToken을 사용하여
        // 사용자 정보와 권한 정보를 포함한 Authentication 객체를 생성하고 반환
        return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
    }
}
