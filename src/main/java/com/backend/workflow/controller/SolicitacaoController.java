package com.backend.workflow.controller;


import com.backend.workflow.repository.PagamentoRepository;
import com.backend.workflow.util.BigDecimalConverter;
import com.backend.workflow.entity.Solicitacao;
import com.backend.workflow.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;
    private final PagamentoRepository pagamentoRepository;

    // Metodo p/ salvar uma solicitacao
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitacao salvar(@RequestBody @Valid Solicitacao solicitacao){
        return repository.save(solicitacao);
    }

    // metodo para achar um cliente pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("{id}")
    public Solicitacao acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Metodo p/ excluir um cliente, se ele existir delete, caso ñ existir trata o erro (not found) (Postman)
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id) {
        repository
                .findById(id)
                .map(solicitacao -> {
                    repository.delete(solicitacao);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Esse metodo atualiza as info de uma solicitacao, no qual só pode editar o nome do fornecedor
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)        // Mensagem no postman após a atualização
    public void atualizar( @PathVariable Integer id, @RequestBody Solicitacao solicitacaoAtualizado) {
        repository
                .findById(id)
                .map(solicitacao -> {
                    solicitacao.setNomeFornecedor(solicitacaoAtualizado.getNomeFornecedor());
                    return repository.save(solicitacaoAtualizado);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
