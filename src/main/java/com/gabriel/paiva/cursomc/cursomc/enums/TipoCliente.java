package com.gabriel.paiva.cursomc.cursomc.enums;

import java.io.Serializable;

public enum TipoCliente {

    PESSOAFISICA (1, "Pessoa física"),
    PESSOAJURIDICA (2, "Pessoa jurídica");

    private Integer code;
    private String descricao;

    private TipoCliente(Integer code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCliente toEnum(Integer code){
        if(code == null)
            return null;

        for(TipoCliente tipoCliente : TipoCliente.values()){
            if(tipoCliente.getCode().equals(code)){
                return tipoCliente;
            }
        }

        throw new IllegalArgumentException("Codigo inválido:" + code);
    }
}
