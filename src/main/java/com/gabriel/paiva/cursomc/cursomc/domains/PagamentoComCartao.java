package com.gabriel.paiva.cursomc.cursomc.domains;

import com.gabriel.paiva.cursomc.cursomc.enums.EstadoPagamento;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class PagamentoComCartao  extends Pagamento implements Serializable {

    Integer numeroParcelas;

    public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroParcelas) {
        super(id,estadoPagamento,pedido);
        this.numeroParcelas = numeroParcelas;
    }

    public PagamentoComCartao() {
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
}
