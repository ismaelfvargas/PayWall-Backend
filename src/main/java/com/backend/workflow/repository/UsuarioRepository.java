package com.backend.workflow.repository;

import com.backend.workflow.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);

    // select count(*) > 0 from usuario where username = :login
    boolean existsByUsername(String username);

}
