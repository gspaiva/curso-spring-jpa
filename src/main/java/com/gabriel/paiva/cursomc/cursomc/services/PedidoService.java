package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.Exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar (Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() ->
            new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

}
