package com.korit.board.service;

import com.korit.board.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;

    public boolean sendAuthMail() {
        String toEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, false, "utf-8");
            helper.setSubject("스프링 부트 사용자 인증 메일 테스트");
            helper.setFrom("mini011029@gmail.com");
            helper.setTo(toEmail);

            String token = jwtProvider.generateAuthMailToken(toEmail);      //이메일 인증을 위한 토큰 발행

            mimeMailMessage.setText(
                    //html로 mail을 전송하기 위함
                    "<div>" +
                            "<h1>사용자 인증 메일</h1>"+
                            "<p>사용자 인증을 완료하려면 아래의 버튼을 클릭하세요.</p>" +
                            "<a href=\"http://localhost:8080/auth/mail?token=" + token + "\">인증하기</a>" +
                    "</div>", "utf-8", "html"
            );
            javaMailSender.send(mimeMailMessage);       //설정한 메시지를 sender를 통해 전달함

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
