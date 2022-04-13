package com.pedroanjos.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pedroanjos.cursomc.entities.Address;
import com.pedroanjos.cursomc.entities.Category;
import com.pedroanjos.cursomc.entities.City;
import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.entities.Order;
import com.pedroanjos.cursomc.entities.OrderItem;
import com.pedroanjos.cursomc.entities.Payment;
import com.pedroanjos.cursomc.entities.PaymentWithCard;
import com.pedroanjos.cursomc.entities.PaymentWithTicket;
import com.pedroanjos.cursomc.entities.Product;
import com.pedroanjos.cursomc.entities.State;
import com.pedroanjos.cursomc.entities.enums.PaymentStatus;
import com.pedroanjos.cursomc.entities.enums.TypeClient;
import com.pedroanjos.cursomc.repositories.AddressRepository;
import com.pedroanjos.cursomc.repositories.CategoryRepository;
import com.pedroanjos.cursomc.repositories.CityRepository;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.repositories.OrderItemRepository;
import com.pedroanjos.cursomc.repositories.OrderRepository;
import com.pedroanjos.cursomc.repositories.PaymentRepository;
import com.pedroanjos.cursomc.repositories.ProductRepository;
import com.pedroanjos.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product p1 = new Product(null, "Computador", 2000.0);
		Product p2 = new Product(null, "Impressora", 800.0);
		Product p3 = new Product(null, "Mouse", 80.0);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);
		
		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gamil.com", "48699519087", TypeClient.PESSOA_FISICA);
		cli1.getPhones().addAll(Arrays.asList("27363323", "9383893"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getAddresses().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		
		Order order1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order order2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);
		
		Payment paym1 = new PaymentWithCard(null, PaymentStatus.QUITADO, order1, 6);
		order1.setPayment(paym1);
		Payment paym2 = new PaymentWithTicket(null, PaymentStatus.PENDENTE, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(paym2);
		
		cli1.getOrders().addAll(Arrays.asList(order1, order2));
		
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(paym1, paym2));
		
		OrderItem oi1 = new OrderItem(order1, p1, 0.00, 1, 2000.0);
		OrderItem oi2 = new OrderItem(order1, p3, 0.00, 2, 80.0);
		OrderItem oi3 = new OrderItem(order2, p2, 100.00, 1, 800.0);
		
		order1.getItens().addAll(Arrays.asList(oi1, oi2));
		order2.getItens().addAll(Arrays.asList(oi3));
		
		p1.getItens().addAll(Arrays.asList(oi1));
		p2.getItens().addAll(Arrays.asList(oi3));
		p3.getItens().addAll(Arrays.asList(oi2));
		
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
	}

}
