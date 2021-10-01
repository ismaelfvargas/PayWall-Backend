package com.backend.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Integer id;

    @Column(length = 11)
    private String tributo;

    @Column(name = "nome_fornecedor", nullable = false, length = 50)
    @NotEmpty(message = "{campo.nomeFornecedor.obrigatorio}")
    private String nomeFornecedor;

    @Column(nullable = false, length = 150)
    private String observacao;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataCadastro;

    @Column(name = "data_vencimento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{campo.dataVencimento.obrigatorio}")
    private Date dataVencimento;

    @Column(name = "data_emissao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{campo.dataEmissao.obrigatorio}")
    private Date dataEmissao;

    @Column(name = "valor_bruto")
    private String valorBruto;

    @Column(name = "valor_liquido", nullable = false)
    @NotEmpty(message = "{campo.valorLiquido.obrigatorio}")
    private String valorLiquido;

    @Column(name = "desconto", length = 11)
    private String desconto;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }

}
