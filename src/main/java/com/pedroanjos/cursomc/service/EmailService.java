package com.pedroanjos.cursomc.service;

import com.pedroanjos.cursomc.entities.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order obj);

    void sendEmail(SimpleMailMessage msg);
}
