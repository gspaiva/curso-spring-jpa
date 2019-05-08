package com.gabriel.paiva.cursomc.cursomc.resources;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import com.gabriel.paiva.cursomc.cursomc.services.CategoriaService;
import com.gabriel.paiva.cursomc.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Pedido pedido = pedidoService.buscar(id);
        return ResponseEntity.ok().body(pedido);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = pedidoService.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }



}
