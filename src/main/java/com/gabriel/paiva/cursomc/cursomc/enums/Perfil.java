package com.gabriel.paiva.cursomc.cursomc.enums;

public enum Perfil {

    ADMIN (1, "ROLE_ADMIN"),
    CLIENTE (2, "ROLE_CLIENTE");

    private Integer code;
    private String descricao;

    private Perfil(Integer code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer code){
        if(code == null)
            return null;

        for(Perfil tipoCliente : Perfil.values()){
            if(tipoCliente.getCode().equals(code)){
                return tipoCliente;
            }
        }

        throw new IllegalArgumentException("Codigo inválido:" + code);
    }
}
