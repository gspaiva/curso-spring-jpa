package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    public Categoria buscar (Integer id){
        return categoriaRepo.findById(id).orElse(null);
    }

}
