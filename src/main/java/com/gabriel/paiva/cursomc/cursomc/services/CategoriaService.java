package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.Exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    public Categoria buscar (Integer id){
        Optional<Categoria> categoria = categoriaRepo.findById(id);
        return categoria.orElseThrow(() ->
            new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

}
