package com.backend.workflow.repository;

import com.backend.workflow.entity.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoStatusRepository extends JpaRepository<StatusSolicitacao, Long> {
}
