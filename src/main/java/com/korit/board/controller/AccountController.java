package com.korit.board.controller;

import com.korit.board.dto.PrincipalRespDto;
import com.korit.board.entity.User;
import com.korit.board.security.PrincipalUser;
import com.korit.board.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final MailService mailService;

    @GetMapping("/account/principal")
    public ResponseEntity<?> getPrincipal() {
        PrincipalUser principalUser =
                (PrincipalUser) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();    // UserDetails이기 때문에 PrincipalUser로 다운캐스팅 할 수 있다.
        User user = principalUser.getUser();
        PrincipalRespDto principalRespDto = user.toPrincipalDto();  // 응답용 dto로 변환
        return ResponseEntity.ok(principalRespDto);
    }

    @PostMapping("/account/mail/auth")
    public ResponseEntity<?> sendAuthenticationMail() {
        return ResponseEntity.ok(mailService.sendAuthMail());
    }

}
