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

@Table(name = "pedidos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal valorBruto;

    @Column(name = "valor_liquido", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal valorLiquido;

    @Column(name = "desconto", length = 11)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal desconto;

    @Column(length = 11)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=19, fraction=2)
    private BigDecimal tributo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_usuario")
    Usuario usuario;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="id_centro_custo")
//    CentroCusto centroCusto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tipo_pedido")
    TipoPedido tipoPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipo_status")
    TipoStatus tipoStatus;

    @ManyToOne(fetch = FetchType.EAGER)
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
