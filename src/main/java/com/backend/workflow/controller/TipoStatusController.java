package com.backend.workflow.controller;

import com.backend.workflow.entity.StatusSolicitacao;
import com.backend.workflow.repository.TipoStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipoStatus")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class TipoStatusController {

    private final TipoStatusRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusSolicitacao salvar(@RequestBody @Valid StatusSolicitacao statusSolicitacao){
        return repository.save(statusSolicitacao);
    }

    @GetMapping
    public List<StatusSolicitacao> obterTodos(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public StatusSolicitacao acharPorId(@PathVariable Long id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado"));
    }
}
