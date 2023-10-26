package com.share2renew.controller;

import com.share2renew.service.IUserService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @program: Share2Renew_BackEnd
 * @description: For sending mail
 * @author: Junxian Cai
 **/


@RestController("/mail")
public class MailController {

    @Autowired
    private IUserService userService;

    @PostMapping("/testSendEmail")
    public String sendMail() throws MessagingException, TemplateException, IOException {
        userService.sendEmail();
        return "111";
    }
}
