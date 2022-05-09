package com.pedroanjos.cursomc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedroanjos.cursomc.dto.ClientDTO;
import com.pedroanjos.cursomc.dto.ClientNewDTO;
import com.pedroanjos.cursomc.entities.Address;
import com.pedroanjos.cursomc.entities.City;
import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.entities.enums.TypeClient;
import com.pedroanjos.cursomc.repositories.AddressRepository;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.service.exceptions.DataIntegrityException;
import com.pedroanjos.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;
	
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
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		 obj = repository.save(obj);
		 addressRepository.saveAll(obj.getAddresses());
		return obj;
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
			throw new DataIntegrityException("Não é possível excluir pois há pedidos relacionados");
		}
	}
	
	public Client fromDTO(ClientDTO dto) {
		return new Client(dto.getId(), dto.getName(), dto.getEmail(), null, null);
	}
	
	public Client fromDTO(ClientNewDTO dto) {
		Client cli = new Client(null, dto.getName(), dto.getEmail(), dto.getCpfOrCnpj(), TypeClient.toEnum(dto.getType()));
		City city = new City(dto.getCityId(), null, null);
		Address address = new Address(null, dto.getStreet(), dto.getNumber(), dto.getComplement(), dto.getDistrict(), dto.getCep(), cli, city);
		cli.getAddresses().add(address);
		cli.getPhones().add(dto.getPhone1());
		if(dto.getPhone2() != null) {
			cli.getPhones().add(dto.getPhone2());
		}
		if(dto.getPhone3() != null) {
			cli.getPhones().add(dto.getPhone3());
		}
		return cli;
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
