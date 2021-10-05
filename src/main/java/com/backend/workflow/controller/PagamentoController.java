package com.backend.workflow.controller;


import com.backend.workflow.entity.Pagamento;
import com.backend.workflow.entity.Solicitacao;
import com.backend.workflow.repository.PagamentoRepository;
import com.backend.workflow.repository.SolicitacaoRepository;
import com.backend.workflow.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento salvar(@RequestBody @Valid Pagamento pagamento){
        return repository.save(pagamento);
    }

//    @GetMapping
//    public List<Pagamento> pesquisar(
//        @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor,
//        @RequestParam(value = "dataEmissao", required = false) Date dataEmissao
//    ) {
//        return repository.findByNomeFornecedorAndDataEmissao( "%" + nomeForcenedor + "%", dataEmissao);
//    }

    // metodo para achar um pagamento pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("/{id}")
    public Pagamento acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
