package com.gabriel.paiva.cursomc.cursomc.repositories;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.domains.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(Pageable pageable, @Param("nome") String nome, @Param("categorias") List<Categoria> categorias);
}
