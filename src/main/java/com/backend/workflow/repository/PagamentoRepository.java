package com.backend.workflow.repository;

import com.backend.workflow.entity.Pagamento;
import com.backend.workflow.entity.Solicitacao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

//    @Query( "select s from Pagamento s join s.solicitacao c " +
//            "where upper( c.nomeFornecedor ) like upper( :nomeFornecedor ) and c.dataEmissao =:dataEmissao " )
//    List<Pagamento> findByNomeFornecedorAndDataEmissao(
//            @Param("nomeFornecedor") String nomeForcenedor, @Param("dataEmissao") Date dataEmissao);

}
