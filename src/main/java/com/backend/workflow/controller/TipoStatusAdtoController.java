package com.backend.workflow.controller;

import com.backend.workflow.entity.StatusAdiantamento;
import com.backend.workflow.repository.TipoStatusAdtoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipoStatusAdto")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class TipoStatusAdtoController {

    private final TipoStatusAdtoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusAdiantamento salvar(@RequestBody @Valid StatusAdiantamento statusAdiantamento){
        return repository.save(statusAdiantamento);
    }

    @GetMapping
    public List<StatusAdiantamento> obterTodos(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public StatusAdiantamento acharPorId(@PathVariable Long id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado"));
    }
}
