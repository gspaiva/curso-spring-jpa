package com.gabriel.paiva.cursomc.cursomc.resources;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar(){
        try{
            Categoria categoria1 = new Categoria(1, "Livros");
            Categoria categoria2 = new Categoria(2, "Filmes");
            List<Categoria> categorias = new ArrayList<>();
            categorias.add(categoria1);
            categorias.add(categoria2);
            return categorias;
        }catch (Exception e){
            throw e;
        }
    }

}
