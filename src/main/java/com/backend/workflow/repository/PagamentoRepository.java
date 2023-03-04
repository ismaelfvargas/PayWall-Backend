package com.backend.workflow.repository;

import com.backend.workflow.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {


    @Query( "select s from Pagamento s join fetch s.tipoStatus t " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus )" )
    List<Pagamento> findByNomeFornecedorAndNomeStatus(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus);

    @Query( "select s from Pagamento s join fetch s.tipoStatus t " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and s.usuario.id = :idUsuario" )
    List<Pagamento> findByNomeFornecedorAndNomeStatusAndUserId(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("idUsuario") Integer idUsuario);

    @Query( "select s from Pagamento s join fetch s.tipoStatus t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area" )
    List<Pagamento> findByNomeFornecedorAndNomeStatusAndArea(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

    @Query( "select s from Pagamento s join fetch s.tipoStatus t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area and u.cargo.roles <> 'GERENTE' and u.cargo.roles <> 'COORDENADOR' and u.cargo.roles <> 'DIRETOR' ")
    List<Pagamento> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteNotCoordenadorNotDiretor(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

//    @Query( "select s from Pagamento s join fetch s.tipoStatus t join fetch s.usuario u " +
//            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.area = :area and u.roles <> 'GERENTE' ")
//    List<Pagamento> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerente(
//            @Param("nomeFornecedor") String nomeFornecedor,
//            @Param("nomeStatus") String nomeStatus,
//            @Param("area") String area);

    @Query( "select s from Pagamento s join fetch s.tipoStatus t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area and u.cargo.roles <> 'GERENTE' and u.cargo.roles <> 'DIRETOR' ")
    List<Pagamento> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteAndNotDiretor(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

    @Transactional
    @Modifying
    @Query("UPDATE Pagamento p set p.tipoStatus.id = :tipoStatus where p.id = :id")
    void aprovarStatus(
            @Param("id") Integer id,
            @Param("tipoStatus") Integer tipoStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Pagamento p set p.tipoStatusAdto.id = :tipoStatusAdto where p.id = :id")
    void alterarStatusAdto(
            @Param("id") Integer id,
            @Param("tipoStatusAdto") Integer tipoStatusAdto);

    @Transactional
    @Modifying
    @Query("UPDATE Pagamento p set p.mensagemReprovacao = :mensagemReprovacao where p.id = :id")
    void inserirMensagemReprovacao(
            @Param("id") Integer id,
            @Param("mensagemReprovacao") String mensagemReprovacao);


    @Query("select p.tipoPedido.nomePedido, p.dataVencimento, count(p.id) from Pagamento p group by p.tipoPedido.nomePedido, p.dataVencimento order by p.dataVencimento asc")
    List<Object[]> queryDashboard();
}
