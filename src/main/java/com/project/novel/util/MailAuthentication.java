package com.project.novel.util;

import com.project.novel.entity.Authentication;
import com.project.novel.repository.AuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailAuthentication {
    private final JavaMailSender javaMailSender;
    private final AuthRepository authRepository;
    private String randomNumber;
    public String createRandomNumber() {
        return randomNumber = "" + ((int) (Math.random() * 90000) + 10000);
    }
    public MimeMessage createMail(String mail) throws MessagingException {
        String randomNum = createRandomNumber();
        Authentication dbInsertCode = Authentication.builder()
                .userEmail(mail)
                .randomCode(randomNum).build();
        authRepository.save(dbInsertCode);
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("alex7006@naver.com");
        message.setRecipients(MimeMessage.RecipientType.TO,mail);
        message.setSubject("이메일 검증");
        String content = "<h2>요청하신 인증번호입니다.</h2>";
        content+="<h1>"+ randomNumber +"</h1>";
        message.setText(content,"UTF-8","html");
        return message;
    }
}
