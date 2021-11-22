package com.backend.workflow.controller;

import com.backend.workflow.entity.TipoStatusAdto;
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
    public TipoStatusAdto salvar(@RequestBody @Valid TipoStatusAdto tipoStatusAdto){
        return repository.save(tipoStatusAdto);
    }

    @GetMapping
    public List<TipoStatusAdto> obterTodos(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public TipoStatusAdto acharPorId( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado") );
    }
}
