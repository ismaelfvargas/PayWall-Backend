package com.backend.workflow.repository;

import com.backend.workflow.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    @Query( "select s from Pedido s join fetch s.statusSolicitacao t " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus )" )
    List<Pedido> findByNomeFornecedorAndNomeStatus(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus);

    @Query( "select s from Pedido s join fetch s.statusSolicitacao t " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and s.usuario.id = :idUsuario" )
    List<Pedido> findByNomeFornecedorAndNomeStatusAndUserId(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("idUsuario") Long idUsuario);

    @Query( "select s from Pedido s join fetch s.statusSolicitacao t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area" )
    List<Pedido> findByNomeFornecedorAndNomeStatusAndArea(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

    @Query( "select s from Pedido s join fetch s.statusSolicitacao t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area and u.cargo.roles <> 'GERENTE' and u.cargo.roles <> 'COORDENADOR' and u.cargo.roles <> 'DIRETOR' ")
    List<Pedido> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteNotCoordenadorNotDiretor(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

//    @Query( "select s from Pagamento s join fetch s.tipoStatus t join fetch s.usuario u " +
//            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.area = :area and u.roles <> 'GERENTE' ")
//    List<Pagamento> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerente(
//            @Param("nomeFornecedor") String nomeFornecedor,
//            @Param("nomeStatus") String nomeStatus,
//            @Param("area") String area);

    @Query( "select s from Pedido s join fetch s.statusSolicitacao t join fetch s.usuario u " +
            "where upper( s.nomeFornecedor ) like upper( :nomeFornecedor ) and upper( t.nomeStatus ) like upper( :nomeStatus ) and u.cargo.area = :area and u.cargo.roles <> 'GERENTE' and u.cargo.roles <> 'DIRETOR' ")
    List<Pedido> findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteAndNotDiretor(
            @Param("nomeFornecedor") String nomeFornecedor,
            @Param("nomeStatus") String nomeStatus,
            @Param("area") String area);

    @Transactional
    @Modifying
    @Query("UPDATE Pedido p set p.statusSolicitacao.id = :tipoStatus where p.id = :id")
    void aprovarStatus(
            @Param("id") Long id,
            @Param("tipoStatus") Long tipoStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Pedido p set p.statusAdiantamento.id = :tipoStatusAdto where p.id = :id")
    void alterarStatusAdto(
            @Param("id") Long id,
            @Param("tipoStatusAdto") Long tipoStatusAdto);

    @Transactional
    @Modifying
    @Query("UPDATE Pedido p set p.mensagemReprovacao = :mensagemReprovacao where p.id = :id")
    void inserirMensagemReprovacao(
            @Param("id") Long id,
            @Param("mensagemReprovacao") String mensagemReprovacao);


    @Query("select p.tipoPedido.nomePedido, p.dataVencimento, count(p.id) from Pedido p group by p.tipoPedido.nomePedido, p.dataVencimento order by p.dataVencimento asc")
    List<Object[]> queryDashboard();
}
