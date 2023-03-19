package com.backend.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "PEDIDOS")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME_FORNECEDOR", nullable = false, length = 50)
    @NotEmpty(message = "campo nome é obrigatorio")
    private String nomeFornecedor;

    @Column(name = "OBSERVACAO", nullable = false, length = 150)
    private String observacao;

    @Column(name = "DATA_CADASTRO", updatable = false)
    private LocalDate dataCadastro;

    @Column(name = "DATA_VENCIMENTO", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    @Column(name = "DATA_EMISSAO", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataEmissao;

    @Column(name = "VALOR_BRUTO")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal valorBruto;

    @Column(name = "VALOR_LIQUIDO", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal valorLiquido;

    @Column(name = "DESCONTO", length = 11)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal desconto;

    @Column(name = "TRIBUTO", length = 11)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal tributo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ID_USUARIO", referencedColumnName="ID")
    Usuario usuario;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="id_centro_custo")
//    CentroCusto centroCusto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ID_TIPO_PEDIDO", referencedColumnName="ID")
    TipoPedido tipoPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ID_TIPO_STATUS", referencedColumnName="ID")
    StatusSolicitacao statusSolicitacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ID_TIPO_STATUS_ADTO", referencedColumnName="ID")
    StatusAdiantamento statusAdiantamento;

    @Column(name = "CENTRO_CUSTO")
    @NotEmpty(message = "campo centro de custo é obrigatorio")
    private String centroDeCusto;

    @Column(name = "MENSAGEM_REPROVACAO")
    private String mensagemReprovacao;

    @Column(name = "PEDIDO_INATIVO")
    private String pedidoInativo;

    @Column(name = "MOTIVO_INATIVACAO")
    private String motivoInativacao;

    @Column(name = "ID_USUARIO_ALT")
    private Long idUsuarioAlt;

    @Column(name = "DATA_PED_INAT")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataPedInat;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }
}
