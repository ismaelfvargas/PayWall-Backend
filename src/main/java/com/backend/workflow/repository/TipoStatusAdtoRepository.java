package com.backend.workflow.repository;


import com.backend.workflow.entity.StatusAdiantamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoStatusAdtoRepository extends JpaRepository<StatusAdiantamento, Long> {
}
