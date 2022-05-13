package com.pedroanjos.cursomc.service;

import com.pedroanjos.cursomc.CursomcApplication;
import com.pedroanjos.cursomc.entities.*;
import com.pedroanjos.cursomc.entities.enums.PaymentStatus;
import com.pedroanjos.cursomc.entities.enums.Profile;
import com.pedroanjos.cursomc.entities.enums.TypeClient;
import com.pedroanjos.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder encoder;

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

    public void instantiateTestDatabase() throws ParseException {

        Category cat1 = new Category(null, "Informática");
        Category cat2 = new Category(null, "Escritório");
        Category cat3 = new Category(null, "Cama mesa e banho");
        Category cat4 = new Category(null, "Eletrônicos");
        Category cat5 = new Category(null, "Jardinagem");
        Category cat6 = new Category(null, "Decoração");
        Category cat7 = new Category(null, "Perfumaria");

        Product p1 = new Product(null, "Computador", 2000.0);
        Product p2 = new Product(null, "Impressora", 800.0);
        Product p3 = new Product(null, "Mouse", 80.0);
        Product p4 = new Product(null, "Mesa de escritório", 300.00);
        Product p5 = new Product(null, "Toalha", 50.00);
        Product p6 = new Product(null, "Colcha", 200.00);
        Product p7 = new Product(null, "TV true color", 1200.00);
        Product p8 = new Product(null, "Roçadeira", 800.00);
        Product p9 = new Product(null, "Abajour", 100.00);
        Product p10 = new Product(null, "Pendente", 180.00);
        Product p11 = new Product(null, "Shampoo", 90.00);

        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProducts().addAll(Arrays.asList(p2, p4));
        cat3.getProducts().addAll(Arrays.asList(p5, p6));
        cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProducts().addAll(Arrays.asList(p8));
        cat6.getProducts().addAll(Arrays.asList(p9, p10));
        cat7.getProducts().addAll(Arrays.asList(p11));

        p1.getCategories().addAll(Arrays.asList(cat1, cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().addAll(Arrays.asList(cat2));
        p5.getCategories().addAll(Arrays.asList(cat3));
        p6.getCategories().addAll(Arrays.asList(cat3));
        p7.getCategories().addAll(Arrays.asList(cat4));
        p8.getCategories().addAll(Arrays.asList(cat5));
        p9.getCategories().addAll(Arrays.asList(cat6));
        p10.getCategories().addAll(Arrays.asList(cat6));
        p11.getCategories().addAll(Arrays.asList(cat7));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        State st1 = new State(null, "Minas Gerais");
        State st2 = new State(null, "São Paulo");

        City c1 = new City(null, "Uberlândia", st1);
        City c2 = new City(null, "São Paulo", st2);
        City c3 = new City(null, "Campinas", st2);

        st1.getCities().addAll(Arrays.asList(c1));
        st2.getCities().addAll(Arrays.asList(c2, c3));

        stateRepository.saveAll(Arrays.asList(st1, st2));
        cityRepository.saveAll(Arrays.asList(c1, c2, c3));

        Client cli1 = new Client(null, "Junior Lima", "moacireletrica4@gmail.com", "48699519087", TypeClient.PESSOA_FISICA, encoder.encode("123"));
        cli1.getPhones().addAll(Arrays.asList("27363323", "9383893"));

        Client cli2 = new Client(null, "Hugo Abaporu", "liaodxurebe@gmail.com", "14896332008", TypeClient.PESSOA_FISICA, encoder.encode("123"));
        cli2.getPhones().addAll(Arrays.asList("54123658", "874569321"));
        cli2.addProfile(Profile.ADMIN);

        Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
        Address a3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "36215964", cli2, c2);

        cli1.getAddresses().addAll(Arrays.asList(a1, a2));
        cli2.getAddresses().addAll(Arrays.asList(a3));

        clientRepository.saveAll(Arrays.asList(cli1, cli2));
        addressRepository.saveAll(Arrays.asList(a1, a2, a3));

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

