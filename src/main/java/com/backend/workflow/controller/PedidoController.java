package com.backend.workflow.controller;


import com.backend.workflow.dto.PedidoDTO;
import com.backend.workflow.entity.*;
import com.backend.workflow.repository.*;
import com.backend.workflow.useCase.PedidoUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository repository;
    private final PedidoUsecase usecase;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido salvar(@RequestBody PedidoDTO dto){
       return usecase.salvar(dto);
    }

    @GetMapping
    public List<Pedido> pesquisar(
        @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeFornecedor,
        @RequestParam(value = "nomeStatus", required = false, defaultValue = "") String nomeStatus){
        return usecase.pesquisar(nomeFornecedor, nomeStatus);
    }

    // metodo para achar um pedido pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("{id}")
    public Pedido acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void atualizar(@PathVariable Integer id, @RequestBody Pedido pedidoAtualizado){
        usecase.atualizar(id, pedidoAtualizado);
    }

    @PutMapping("/atualizandoStatus/{id}/{tipoStatus}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void aprovarStatus(@PathVariable int id, @PathVariable int tipoStatus){
        repository.aprovarStatus(id, tipoStatus);
    }

    @PutMapping("/atualizandoStatusAdto/{id}/{tipoStatusAdto}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void alterarStatusAdto(@PathVariable int id, @PathVariable int tipoStatusAdto){
        repository.alterarStatusAdto(id, tipoStatusAdto);
    }

    @PutMapping("/inserirMensagemReprovacao/{id}/{mensagemReprovacao}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inserirMensagemReprovacao(@PathVariable int id, @PathVariable String mensagemReprovacao){
        repository.inserirMensagemReprovacao(id, mensagemReprovacao);
    }

    @GetMapping("/teste")
    public List<Pedido> pesquisarMeusPedidos(
            @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor,
            @RequestParam(value = "nomeStatus", required = false, defaultValue = "") String nomeStatus){
        return usecase.pesquisarMeusPedidos(nomeForcenedor, nomeStatus);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarPedido( @PathVariable Integer id ){
        usecase.inativarPedido(id);
    }

    @GetMapping("/dashboard")
    public List<Object[]> dashboard(){
        return repository.queryDashboard();
    }
}
