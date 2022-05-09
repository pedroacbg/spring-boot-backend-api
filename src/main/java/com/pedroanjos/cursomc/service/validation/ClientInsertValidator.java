package com.pedroanjos.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pedroanjos.cursomc.dto.ClientNewDTO;
import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.entities.enums.TypeClient;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.resources.exceptions.FieldMessage;
import com.pedroanjos.cursomc.service.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getType().equals(TypeClient.PESSOA_FISICA.getCode()) && !BR.isValidCPF(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
		}

		if(objDto.getType().equals(TypeClient.PESSOA_JURIDICA.getCode()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
		}
		
		Client aux = repository.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já registrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
