package com.backend.workflow.controller;

import com.backend.workflow.entity.Usuario;
import com.backend.workflow.useCase.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase useCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        useCase.salvar(usuario);
    }

    @GetMapping("/permissao/menu")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void permissao(){
        useCase.permissao();
    }

    @GetMapping("/permissao/assistente")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void permissaoAssistente(){
        useCase.permissaoAssistente();
    }

    @GetMapping("/permissao/administrador")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void permissaoAdministrador(){
        useCase.permissaoAdministrador();
    }
}
