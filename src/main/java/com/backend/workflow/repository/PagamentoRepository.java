package com.backend.workflow.repository;

import com.backend.workflow.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @Query( "select s from Pagamento s join s.tipoPedido c " +
            "where upper( c.nomePedido ) like upper( :nomePedido ) and upper( s.nomeFornecedor ) like upper( :nomeFornecedor )" )
    List<Pagamento> findByNomeFornecedorAndNomePedido(
            @Param("nomePedido") String nomePedido, @Param("nomeFornecedor") String nomeFornecedor);

}
