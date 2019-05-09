package com.gabriel.paiva.cursomc.cursomc.dtos;

import java.io.Serializable;

public class CrendenciaisDTO implements Serializable {

    private String email;
    private String senha;

    public CrendenciaisDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
