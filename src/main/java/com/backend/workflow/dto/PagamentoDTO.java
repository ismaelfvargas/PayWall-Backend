package com.backend.workflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
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
    private String valorBruto;
    private String valorLiquido;
    private String desconto;
    private String tributo;
    private Integer idUsuario;
    private Integer idCentroDeCusto;
    private Integer idTipoPedido;
    private Integer idTipoStatus;

    // inclui a data automatico
    @PrePersist
    public void insereDataAutomatico() {
        setDataCadastro(LocalDate.now());
    }

}