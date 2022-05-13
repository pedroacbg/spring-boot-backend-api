package com.pedroanjos.cursomc.service;

import java.util.Date;
import java.util.Optional;

import com.pedroanjos.cursomc.entities.OrderItem;
import com.pedroanjos.cursomc.entities.PaymentWithTicket;
import com.pedroanjos.cursomc.entities.enums.PaymentStatus;
import com.pedroanjos.cursomc.repositories.OrderItemRepository;
import com.pedroanjos.cursomc.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroanjos.cursomc.entities.Order;
import com.pedroanjos.cursomc.repositories.OrderRepository;
import com.pedroanjos.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EmailService emailService;
	
	public Order findById(Long id){
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.findById(obj.getClient().getId()));
		obj.getPayment().setStatus(PaymentStatus.PENDENTE);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentWithTicket) {
			PaymentWithTicket paymt = (PaymentWithTicket) obj.getPayment();
			ticketService.fillPaymentWithTicket(paymt, obj.getInstant());
		}
		obj = repository.save(obj);
		paymentRepository.save(obj.getPayment());
		for (OrderItem oi : obj.getItens()) {
			oi.setDiscount(0.0);
			oi.setProduct(productService.findById(oi.getProduct().getId()));
			oi.setPrice(oi.getProduct().getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
