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

}
