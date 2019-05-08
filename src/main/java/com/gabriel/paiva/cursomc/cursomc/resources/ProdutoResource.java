package com.gabriel.paiva.cursomc.cursomc.resources;

import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import com.gabriel.paiva.cursomc.cursomc.domains.Produto;
import com.gabriel.paiva.cursomc.cursomc.dtos.ProdutoDTO;
import com.gabriel.paiva.cursomc.cursomc.resources.utils.URL;
import com.gabriel.paiva.cursomc.cursomc.services.PedidoService;
import com.gabriel.paiva.cursomc.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Produto produto = produtoService.buscar(id);
        return ResponseEntity.ok().body(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name="nome") String nome,
            @RequestParam(name="ids") String ids,
            @RequestParam(name="page", defaultValue="0") Integer page,
            @RequestParam(name="linesPerPage", defaultValue="5") Integer linesPerPage,
            @RequestParam(name="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(name="direction", defaultValue="ASC") String direction){

        List<Integer> idsInt = URL.getIdsFromStringParam(ids);
        Page<Produto> produtos = produtoService.search(nome,idsInt,page,linesPerPage,orderBy,direction);
        Page<ProdutoDTO> produtoDTOS = produtos.map(produto -> new ProdutoDTO(produto));
        return ResponseEntity.ok().body(produtoDTOS);
    }




}
