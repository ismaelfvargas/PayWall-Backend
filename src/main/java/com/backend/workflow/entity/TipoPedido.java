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


public class TipoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pedido")
    private Integer id;

}
