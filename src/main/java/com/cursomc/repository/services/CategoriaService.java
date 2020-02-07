package com.cursomc.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repository.services.exceptions.DataIntegrityException;
import com.cursomc.repository.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	
	public Categoria findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	public Categoria insert(Categoria obj) {
		return repository.save(obj);
	}
	
	public Categoria update(Long id, Categoria obj) {
		Categoria cat = findById(id);
		updateCategoria(obj,cat);
		return repository.save(cat);
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
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}

	private void updateCategoria(Categoria obj, Categoria cat) {
		cat.setNome(obj.getNome());
	}
	
}
