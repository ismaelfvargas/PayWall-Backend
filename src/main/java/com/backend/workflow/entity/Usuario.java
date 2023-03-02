package com.backend.workflow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(unique = true, name = "nome")
    @NotEmpty(message = "{O campo login é obrigatório}")
    @Email
    private String username;

    @Column(name = "senha", nullable = false)
    @NotEmpty(message = "{O campo senha é obrigatório}")
    private String password;

    @Column(nullable = false)
    @NotEmpty(message = "{O campo role é obrigatório}")
    private String roles;

    @Column(nullable = false)
    @NotEmpty(message = "{O campo area é obrigatório}")
    private String area;
}
