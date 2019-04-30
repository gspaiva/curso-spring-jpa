package com.gabriel.paiva.cursomc.cursomc.repositories;

import com.gabriel.paiva.cursomc.cursomc.domains.Pagamento;
import com.gabriel.paiva.cursomc.cursomc.domains.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
