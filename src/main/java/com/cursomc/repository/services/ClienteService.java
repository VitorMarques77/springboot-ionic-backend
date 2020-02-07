package com.cursomc.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repository.services.exceptions.DataIntegrityException;
import com.cursomc.repository.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	
	public Cliente findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	public Cliente update(Long id, Cliente obj) {
		Cliente cli = findById(id);
		updateCliente(obj,cli);
		return repository.save(cli);
	}
	
	public void deleteById(Long id) {
		findById(id);
		try {
		repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(e.getMessage());
		}
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repository.findAll(pageRequest);
	}

	private void updateCliente(Cliente obj, Cliente cli) {
		cli.setNome(obj.getNome());
		cli.setEmail(obj.getEmail());
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		 return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	
	}
	
}
