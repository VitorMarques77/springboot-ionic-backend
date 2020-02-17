package com.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
		
	}
	
	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		
		// atribuiu o request passado em um map
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map <String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Long uriId = Long.parseLong(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		//checagem das condicoes e adicao dos erros na nossa lista
		
		Cliente aux = repository.findByEmail(objDTO.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente!"));
		}
		
		//desabilitar a lista de erros default e adicionar os erros da nossa lista
		for(FieldMessage x:list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(x.getMessage())
			.addPropertyNode(x.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}

}
