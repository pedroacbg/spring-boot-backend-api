package com.pedroanjos.cursomc.service;

import com.pedroanjos.cursomc.entities.Order;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Order obj);

    void sendHtmlEmail(MimeMessage msg);
}
