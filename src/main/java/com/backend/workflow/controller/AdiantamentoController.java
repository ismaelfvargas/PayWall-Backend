package com.backend.workflow.controller;


import com.backend.workflow.entity.Adiantamento;
import com.backend.workflow.entity.Pagamento;
import com.backend.workflow.repository.AdiantamentoRepository;
import com.backend.workflow.repository.SolicitacaoRepository;
import com.backend.workflow.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/adiantamentos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class AdiantamentoController {

    private final AdiantamentoRepository repository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final BigDecimalConverter bigDecimalConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Adiantamento adiantamento){
        solicitacaoRepository.save(adiantamento.getSolicitacao());
        repository.save(adiantamento);
    }

    // metodo para achar um pagamento pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("/{id}")
    public Adiantamento acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
