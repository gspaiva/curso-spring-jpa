package com.gabriel.paiva.cursomc.cursomc;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.domains.Produto;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;

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

            categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
            produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));


        }catch (Exception e){
            throw e;
        }
    }
}
