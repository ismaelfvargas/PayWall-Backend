package com.backend.workflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class PagamentoDTO {

    private String nomeFornecedor;
    private String observacao;
    private LocalDate dataCadastro;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataEmissao;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido;
    private BigDecimal desconto;
    private BigDecimal tributo;
    private String centroDeCusto;
    private Integer idUsuario;
    private Integer idTipoPedido;
    private Integer idTipoStatus;
    private Integer idTipoStatusAdto;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }

}