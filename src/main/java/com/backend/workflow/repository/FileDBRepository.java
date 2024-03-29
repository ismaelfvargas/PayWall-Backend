package com.backend.workflow.repository;

import com.backend.workflow.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    @Transactional
    @Query( "select f from FileDB f where f.idSolicitacao = :idSolicitacao " )
    List<FileDB> findByIdPagamento(
            @Param("idSolicitacao") Long idSolicitacao);
}
