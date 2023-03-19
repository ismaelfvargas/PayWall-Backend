package com.backend.workflow.controller;

import com.backend.workflow.entity.TipoPedido;
import com.backend.workflow.repository.TipoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/tipoPedido")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class TipoPedidoController {

    private final TipoPedidoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoPedido salvar(@RequestBody @Valid TipoPedido tipoPedido){
        return repository.save(tipoPedido);
    }

    @GetMapping
    public List<TipoPedido> obterTodos(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public TipoPedido acharPorId( @PathVariable Long id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada") );
    }

}
