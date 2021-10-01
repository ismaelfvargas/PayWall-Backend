package com.backend.workflow.repository;

import com.backend.workflow.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer> {

}
