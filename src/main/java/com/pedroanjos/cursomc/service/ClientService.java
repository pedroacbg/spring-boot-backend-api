package com.pedroanjos.cursomc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedroanjos.cursomc.dto.ClientDTO;
import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.service.exceptions.DataIntegrityException;
import com.pedroanjos.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	public List<ClientDTO> findAll(){
		List<Client> list = repository.findAll();
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}
	
	
	public Client findById(Long id){
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	public Page<ClientDTO> findPage(Pageable pageable){
		Page<Client> list = repository.findAll(pageable);
		return list.map(x -> new ClientDTO(x));
	}

	public Client update(Client obj) {
		Client newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas");
		}
	}
	
	public Client fromDTO(ClientDTO dto) {
		return new Client(dto.getId(), dto.getName(), dto.getEmail(), null, null);
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
