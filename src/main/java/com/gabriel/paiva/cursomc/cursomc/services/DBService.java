package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.*;
import com.gabriel.paiva.cursomc.cursomc.enums.EstadoPagamento;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void DbSeed() throws Exception{
        Categoria categoria1 = new Categoria(null, "Informatica");
        Categoria categoria2 = new Categoria(null, "Escritório");
        Categoria categoria3 = new Categoria(null, "Cama mesa e banho");
        Categoria categoria4 = new Categoria(null, "Livros");
        Categoria categoria5 = new Categoria(null, "Celulares");
        Categoria categoria6 = new Categoria(null, "Restaurante");
        Categoria categoria7 = new Categoria(null, "Nova farmácia");
        Categoria categoria8 = new Categoria(null, "5AmClub");

        Produto produto1 = new Produto(null, "Computador", 2000d);
        Produto produto2 = new Produto(null, "Impressora", 800d);
        Produto produto3 = new Produto(null, "Mouse", 80d);
        Produto produto4 = new Produto(null, "Mesa de escritório", 300d);
        Produto produto5 = new Produto(null, "Toalha", 50d);
        Produto produto6 = new Produto(null, "Colcha", 200d);
        Produto produto7 = new Produto(null, "TV true color", 1200d);
        Produto produto8 = new Produto(null, "Roçadeira", 800d);
        Produto produto9 = new Produto(null, "Abajour", 100d);
        Produto produto10 = new Produto(null, "Pendente", 180d);
        Produto produto11 = new Produto(null, "Shampoo", 90d);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1,produto2,produto3,produto7));
        categoria5.getProdutos().addAll(Arrays.asList(produto8));
        categoria6.getProdutos().addAll(Arrays.asList(produto9,produto10));
        categoria7.getProdutos().addAll(Arrays.asList(produto11));


        produto1.getCategorias().addAll(Arrays.asList(categoria1,categoria4));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(categoria1,categoria4));
        produto4.getCategorias().addAll(Arrays.asList(categoria2));
        produto5.getCategorias().addAll(Arrays.asList(categoria3));
        produto6.getCategorias().addAll(Arrays.asList(categoria3));
        produto7.getCategorias().addAll(Arrays.asList(categoria4));
        produto8.getCategorias().addAll(Arrays.asList(categoria5));
        produto9.getCategorias().addAll(Arrays.asList(categoria6));
        produto10.getCategorias().addAll(Arrays.asList(categoria6));
        produto11.getCategorias().addAll(Arrays.asList(categoria7));

        Estado minasGerais = new Estado(null, "Minas Gerais");
        Estado saoPauloEstado = new Estado(null, "São Paulo");

        Cidade uberlandia = new Cidade(null, "Uberlandia",minasGerais);
        Cidade campinas = new Cidade(null, "Campinas",saoPauloEstado);
        Cidade saoPauloCidade = new Cidade(null, "São Paulo",saoPauloEstado);

        Cliente maria = new Cliente(null, "Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOAFISICA,bCryptPasswordEncoder.encode("123"));
        maria.getTelefones().add("2722222");
        maria.getTelefones().add("2711111");

        Endereco endereco1 = new Endereco(null, "Rua flores","300","Apt 203","Jardins","64654",uberlandia,maria);
        Endereco endereco2 = new Endereco(null, "Avenida mattos","105","Sala 800","Centro","65655",saoPauloCidade,maria);

        maria.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));

        estadoRepository.saveAll(Arrays.asList(minasGerais, saoPauloEstado));
        cidadeRepository.saveAll(Arrays.asList(uberlandia,campinas,saoPauloCidade));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4,categoria5,categoria6,categoria7,categoria8));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3,produto4,produto5,produto6,produto7,produto8,produto9,produto10,produto11));

        clienteRepository.save(maria);
        enderecoRepository.saveAll(Arrays.asList(endereco1,endereco2));


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),endereco1,maria);
        Pagamento pagamentoComCartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamentoComCartao);

        Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), endereco2,maria);
        Pagamento pagamentoComBoleto = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE, pedido2, sdf2.parse("20/10/2017"), null);
        pedido2.setPagamento(pagamentoComBoleto);

        maria.getPedidos().addAll(Arrays.asList(pedido1,pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1,pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamentoComBoleto,pagamentoComCartao));


        ItemPedido itemPedido1 = new ItemPedido(produto1,pedido1,0d, 1, 2000d);
        ItemPedido itemPedido2 = new ItemPedido(produto3,pedido1,0d, 2, 80d);
        ItemPedido itemPedido3 = new ItemPedido(produto2,pedido2,0d, 1, 2000d);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Arrays.asList(itemPedido3));

        produto1.getItens().addAll(Arrays.asList(itemPedido1));
        produto2.getItens().addAll(Arrays.asList(itemPedido3));
        produto3.getItens().addAll(Arrays.asList(itemPedido2));

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }

}
