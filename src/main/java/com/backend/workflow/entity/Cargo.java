package com.backend.workflow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cargos")
public class Cargo {

    @Id
    @Column(name = "cargo_id")
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "{O campo role é obrigatório}")
    private String roles;

    @Column(nullable = false)
    @NotEmpty(message = "{O campo area é obrigatório}")
    private String area;
}
