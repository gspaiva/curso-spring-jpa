package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.ItemPedido;
import com.gabriel.paiva.cursomc.cursomc.domains.PagamentoComBoleto;
import com.gabriel.paiva.cursomc.cursomc.domains.PagamentoComCartao;
import com.gabriel.paiva.cursomc.cursomc.enums.EstadoPagamento;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import com.gabriel.paiva.cursomc.cursomc.repositories.ItemPedidoRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PagamentoRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
