package com.backend.workflow.enums;

import lombok.Getter;

@Getter
public enum PerfisFuncoesEnum {

    ADMINISTRATIVE("ADMINISTRADOR", "Administrador"),
    ASSISTENT("ASSISTENTE", "Assistente"),
    COORDINATOR("COORDENADOR", "Coordenador"),
    DIRECTOR("DIRETOR", "Diretor"),
    MANAGER("GERENTE", "Gerente"),
    USER("USUARIO", "Usuario");

    private String id;
    private String description;

    PerfisFuncoesEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }

}
