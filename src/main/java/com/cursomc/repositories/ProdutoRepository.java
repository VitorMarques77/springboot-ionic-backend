package com.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE lower(obj.nome) LIKE CONCAT('%', lower(:nome) , '%') AND cat IN :categorias")
	// outra forma de fazer usando keywords do JPA
	Page<Produto> findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(@Param("nome")String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
}
