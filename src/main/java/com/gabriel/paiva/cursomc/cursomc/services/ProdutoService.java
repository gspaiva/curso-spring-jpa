package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import com.gabriel.paiva.cursomc.cursomc.domains.Produto;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PedidoRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscar (Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() ->
            new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

    public Page<Produto> search(String nome, List<Integer> ids,Integer page,
                                Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(pageRequest,nome, categorias);
    }

}
