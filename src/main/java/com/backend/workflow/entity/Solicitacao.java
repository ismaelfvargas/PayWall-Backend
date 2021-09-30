package com.backend.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "solicitacoes")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Column(name = "nome_fornecedor", nullable = false, length = 50)
    @NotEmpty(message = "campo nome é obrigatorio")
    private String nomeFornecedor;

    @Column(nullable = false, length = 150)
    private String observacao;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataCadastro;

    @Column(name = "data_vencimento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    @Column(name = "data_emissao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataEmissao;

    @Column(name = "valor_bruto")
    @NotEmpty(message = "campo valor bruto é obrigatorio")
    private String valorBruto;

    @Column(name = "valor_liquido", nullable = false)
    @NotEmpty(message = "campo valor líquido é obrigatorio")
    private String valorLiquido;

    @Column(name = "desconto", length = 11)
    private String desconto;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }

}
