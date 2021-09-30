package com.backend.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data //cria todos os getter,setters e construtores
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cc;

    @Column(name = "tipo_cc", nullable = false, length = 50)
    @NotEmpty(message = "campo Centro de Custos é obrigatório")
    private String tipoCentroCusto;
    
}
