package com.gabriel.paiva.cursomc.cursomc;

import com.gabriel.paiva.cursomc.cursomc.domains.*;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    EstadoRepository estadoRepository;
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            Categoria categoria1 = new Categoria(null, "Informatica");
            Categoria categoria2 = new Categoria(null, "Escritório");

            Produto produto1 = new Produto(null, "Computador", 2000d);
            Produto produto2 = new Produto(null, "Impressora", 800d);
            Produto produto3 = new Produto(null, "Mouse", 80d);

            categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
            categoria2.getProdutos().addAll(Arrays.asList(produto2));

            produto1.getCategorias().addAll(Arrays.asList(categoria1));
            produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
            produto3.getCategorias().addAll(Arrays.asList(categoria1));

            Estado minasGerais = new Estado(null, "Minas Gerais");
            Estado saoPauloEstado = new Estado(null, "São Paulo");

            Cidade uberlandia = new Cidade(null, "Uberlandia",minasGerais);
            Cidade campinas = new Cidade(null, "Campinas",saoPauloEstado);
            Cidade saoPauloCidade = new Cidade(null, "São Paulo",saoPauloEstado);

            Cliente maria = new Cliente(null, "Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOAFISICA);
            maria.getTelefones().add("2722222");
            maria.getTelefones().add("2711111");

            Endereco endereco1 = new Endereco(null, "Rua flores","300","Apt 203","Jardins","64654",uberlandia,maria);
            Endereco endereco2 = new Endereco(null, "Avenida mattos","105","Sala 800","Centro","65655",saoPauloCidade,maria);

            maria.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));

            estadoRepository.saveAll(Arrays.asList(minasGerais, saoPauloEstado));
            cidadeRepository.saveAll(Arrays.asList(uberlandia,campinas,saoPauloCidade));
            categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
            produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
            clienteRepository.save(maria);
            enderecoRepository.saveAll(Arrays.asList(endereco1,endereco2));


        }catch (Exception e){
            throw e;
        }
    }
}
