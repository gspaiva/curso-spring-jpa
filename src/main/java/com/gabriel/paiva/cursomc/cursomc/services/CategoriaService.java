package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.exceptions.DataIntegrityException;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    public Categoria find(Integer id){
        Optional<Categoria> categoria = categoriaRepo.findById(id);
        return categoria.orElseThrow(() ->
            new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        categoria = categoriaRepo.save(categoria);
        return categoria;
    }

    public Categoria update(Categoria categoria){
        this.find(categoria.getId());
        return categoriaRepo.save(categoria);
    }

    public void delete(Integer id){
        this.find(id);
        try{
            categoriaRepo.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir a categoria pois a mesma possuí produtos vinculados.", e);
        }
    }

    public List<Categoria> findAll(){
        return categoriaRepo.findAll();
    }

}
