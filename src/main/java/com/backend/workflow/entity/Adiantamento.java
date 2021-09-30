package com.backend.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "adiantamentos")
public class Adiantamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adiantamento")
    private Integer id;

    @Column(name = "status_adiantamento", length = 15)
    private String statusAdiantamento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id_solicitacao")
    private Solicitacao solicitacao;

}
