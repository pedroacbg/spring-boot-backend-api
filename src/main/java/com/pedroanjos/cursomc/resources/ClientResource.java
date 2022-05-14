package com.pedroanjos.cursomc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pedroanjos.cursomc.dto.ClientDTO;
import com.pedroanjos.cursomc.dto.ClientNewDTO;
import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<ClientDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClientDTO>> findPage(Pageable pageable){
		Page<ClientDTO> list = service.findPage(pageable);
		return ResponseEntity.ok().body(list); 
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO dto){
		Client obj = service.fromDTO(dto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto){
		Client obj = service.fromDTO(dto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
