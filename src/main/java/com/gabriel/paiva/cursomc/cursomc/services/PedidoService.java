package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.*;
import com.gabriel.paiva.cursomc.cursomc.enums.EstadoPagamento;
import com.gabriel.paiva.cursomc.cursomc.exceptions.AuthorizationException;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.repositories.ItemPedidoRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PagamentoRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PedidoRepository;
import com.gabriel.paiva.cursomc.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public Pedido buscar (Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() ->
            new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

    @Transactional
    public Pedido insert(Pedido pedido){
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setPedido(pedido);
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);

        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
        }

        pedido = pedidoRepository.save(pedido);

        pagamentoRepository.save(pedido.getPagamento());

         //inserir os itens
        for(ItemPedido itemPedido : pedido.getItens()) {
            itemPedido.setDesconto(0d);
            itemPedido.setPedido(pedido);
            itemPedido.setPreco(produtoService.buscar(itemPedido.getProduto().getId()).getPreco());
        }

        itemPedidoRepository.saveAll(pedido.getItens());

        return pedido;
    }

    public Page<Pedido> findPage(UserSS userSS, Integer page, Integer linesPerPage, String orderBy, String direction){

        if(userSS == null){
            throw new AuthorizationException("Access denied");
        }
        Cliente cliente = clienteService.find(userSS.getId());
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return pedidoRepository.findByCliente(cliente,pageRequest);
    }


}
