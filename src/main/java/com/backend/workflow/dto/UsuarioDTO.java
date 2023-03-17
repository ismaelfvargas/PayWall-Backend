package com.backend.workflow.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UsuarioDTO {

    private String email;
    private String password;
    private Long cargoId;
    private String name;
}
