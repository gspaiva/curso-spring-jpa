package com.gabriel.paiva.cursomc.cursomc.repositories;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
