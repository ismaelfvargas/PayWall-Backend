package com.backend.workflow.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UsuarioDTO {

    private String username;
    private String password;
    private Long cargoId;
}
