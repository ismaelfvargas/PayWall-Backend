package com.backend.workflow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(unique = true, name = "nome")
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String username;

    @Column(name = "senha", nullable = false)
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String password;

}
