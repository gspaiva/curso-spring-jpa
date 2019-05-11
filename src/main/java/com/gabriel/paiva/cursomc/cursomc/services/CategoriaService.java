package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.dtos.CategoriaDTO;
import com.gabriel.paiva.cursomc.cursomc.exceptions.DataIntegrityException;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        categoria = categoriaRepo.save(categoria);
        return categoria;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Categoria update(Categoria obj){
        Categoria newObj = this.find(obj.getId());
        updateData(newObj, obj);
        return categoriaRepo.save(newObj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage,Sort.Direction.valueOf(direction),orderBy);
        return categoriaRepo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria newObj,Categoria obj){
        newObj.setNome(obj.getNome());

    }

}
