package com.pedroanjos.cursomc.service;

import com.pedroanjos.cursomc.entities.PaymentWithTicket;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TicketService {

    public void fillPaymentWithTicket(PaymentWithTicket paymt, Date instantOfPayment) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instantOfPayment);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        paymt.setPaymentDate(cal.getTime());
    }
}
