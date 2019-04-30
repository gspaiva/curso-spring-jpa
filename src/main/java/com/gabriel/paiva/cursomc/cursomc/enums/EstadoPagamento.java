package com.gabriel.paiva.cursomc.cursomc.enums;

public enum EstadoPagamento {

    PENDENTE (1, "Pendente"),
    QUITADO (2, "Quitado"),
    CANCELADO (3, "Cancelado");

    private Integer code;
    private String descricao;

    private EstadoPagamento(Integer code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer code){
        if(code == null)
            return null;

        for(EstadoPagamento estadoPagamento : EstadoPagamento.values()){
            if(estadoPagamento.getCode().equals(code)){
                return estadoPagamento;
            }
        }

        throw new IllegalArgumentException("Codigo inválido:" + code);
    }
}
