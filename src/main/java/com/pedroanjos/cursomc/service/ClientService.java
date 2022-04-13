package com.pedroanjos.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	public Client findById(Long id){
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
}
