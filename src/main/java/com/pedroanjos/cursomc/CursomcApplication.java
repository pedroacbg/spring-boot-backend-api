package com.pedroanjos.cursomc;

import com.pedroanjos.cursomc.entities.*;
import com.pedroanjos.cursomc.entities.enums.PaymentStatus;
import com.pedroanjos.cursomc.entities.enums.TypeClient;
import com.pedroanjos.cursomc.repositories.*;
import com.pedroanjos.cursomc.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
