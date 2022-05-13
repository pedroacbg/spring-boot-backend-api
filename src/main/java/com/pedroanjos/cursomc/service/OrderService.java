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
	
	public Order findById(Long id){
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
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
			oi.setPrice(productService.findById(oi.getProduct().getId()).getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItens());
		return obj;
	}
}
