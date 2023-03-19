package com.backend.workflow.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CARGOS")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "CARGOS_ID_SEQ", sequenceName = "CARGOS_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name="ROLES")
    @NotEmpty(message = "{O campo role é obrigatório}")
    private String roles;

    @Column(name="AREA")
    @NotEmpty(message = "{O campo area é obrigatório}")
    private String area;
}
