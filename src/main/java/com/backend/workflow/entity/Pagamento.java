package com.backend.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "pedido")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pagamento")
    private Integer id;

    @Column(name = "nome_fornecedor", nullable = false, length = 50)
    @NotEmpty(message = "campo nome é obrigatorio")
    private String nomeFornecedor;

    @Column(nullable = false, length = 150)
    private String observacao;

    @Column(name = "data_cadastro", updatable = false)
    private LocalDate dataCadastro;

    @Column(name = "data_vencimento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    @Column(name = "data_emissao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataEmissao;

    @Column(name = "valor_bruto")
    private String valorBruto;

    @Column(name = "valor_liquido", nullable = false)
    @NotEmpty(message = "campo valor líquido é obrigatorio")
    private String valorLiquido;

    @Column(name = "desconto", length = 11)
    private String desconto;

    @Column(length = 11)
    private String tributo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_usuario")
    Usuario usuario;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="id_centro_custo")
//    CentroCusto centroCusto;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="tipo_pedido")
    TipoPedido tipoPedido;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_tipo_status")
    TipoStatus tipoStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_tipo_status_adto")
    TipoStatusAdto tipoStatusAdto;

    @Column(name = "centro_custo")
    @NotEmpty(message = "campo centro de custo é obrigatorio")
    private String centroDeCusto;

    @Column(name = "mensagem_reprovacao")
    private String mensagemReprovacao;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }

}
