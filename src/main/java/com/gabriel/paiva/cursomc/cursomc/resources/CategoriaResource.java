package com.gabriel.paiva.cursomc.cursomc.resources;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Categoria categoria = categoriaService.buscar(id);
        return ResponseEntity.ok().body(categoria);
    }

}
