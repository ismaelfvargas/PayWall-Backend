package com.backend.workflow.repository;

import com.backend.workflow.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @Query( "select s from Pagamento s join s.tipoPedido c " +
            "join s.tipoStatus x " +
            "where upper( c.nomePedido ) like upper( :nomePedido ) " +
            "and upper( s.nomeFornecedor ) like upper( :nomeFornecedor )" +
            "and upper( x.nomeStatus ) like upper( :nomeStatus )")
    List<Pagamento> findByNomeFornecedorAndNomePedido(
            @Param("nomePedido") String nomePedido, @Param("nomeFornecedor") String nomeFornecedor, @Param("nomeStatus") String nomeStatus);

//    @Query( "select x from Pagamento x join x.tipoStatus s " +
//            "where upper( s.nomeStatus ) like upper( :nomeStatus ) and upper( x.nomeFornecedor ) like upper( :nomeFornecedor )" )
//    List<Pagamento> findByNomeFornecedorAndNomeStatus(
//            @Param("nomeStatus") String nomeStatus, @Param("nomeFornecedor") String nomeFornecedor);

}
