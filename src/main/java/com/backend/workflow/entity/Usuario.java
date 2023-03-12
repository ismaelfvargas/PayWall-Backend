package com.backend.workflow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CARGO_ID", referencedColumnName="ID")
    private Cargo cargo;
}
