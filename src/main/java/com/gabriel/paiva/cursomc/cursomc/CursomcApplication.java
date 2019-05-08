package com.gabriel.paiva.cursomc.cursomc;

import com.gabriel.paiva.cursomc.cursomc.domains.*;
import com.gabriel.paiva.cursomc.cursomc.enums.EstadoPagamento;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.*;
import com.gabriel.paiva.cursomc.cursomc.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    DBService service;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            service.DbSeed();
        }catch (Exception e){
            throw e;
        }
    }
}
