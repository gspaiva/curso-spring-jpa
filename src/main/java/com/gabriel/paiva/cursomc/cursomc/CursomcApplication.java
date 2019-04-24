package com.gabriel.paiva.cursomc.cursomc;

import com.gabriel.paiva.cursomc.cursomc.domains.Categoria;
import com.gabriel.paiva.cursomc.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    CategoriaRepository categoriaRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            Categoria categoria1 = new Categoria(null, "Informatica");
            Categoria categoria2 = new Categoria(null, "Escritório");
            categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        }catch (Exception e){
            throw e;
        }
    }
}
