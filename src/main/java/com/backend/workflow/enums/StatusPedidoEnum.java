package com.backend.workflow.enums;

import lombok.Getter;

@Getter
public enum StatusPedidoEnum {

    S("S", "SIM"),
    N("N", "NÃO");

    private String id;
    private String description;

    StatusPedidoEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }

}
