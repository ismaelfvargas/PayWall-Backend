package com.backend.workflow.controller;


import com.backend.workflow.dto.PagamentoDTO;
import com.backend.workflow.entity.Pagamento;
import com.backend.workflow.entity.TipoPedido;
import com.backend.workflow.repository.PagamentoRepository;
import com.backend.workflow.repository.TipoPedidoRepository;
import com.backend.workflow.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PrePersist;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoRepository repository;
    private final TipoPedidoRepository tipoPedidoRepository;
    private final BigDecimalConverter bigDecimalConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento salvar(@RequestBody PagamentoDTO dto){
//        LocalDate dataLocal = LocalDate.parse(dto.getDataCadastro(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Integer idTipoPedido = dto.getIdTipoPedido();

        TipoPedido tipoPedido = tipoPedidoRepository.findById(idTipoPedido).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Tipo do pedido inexistente"
                )
        );

        Pagamento pagamento = new Pagamento();
        pagamento.setTributo(dto.getTributo());
        pagamento.setDataCadastro(dto.getDataCadastro());
        pagamento.setDataEmissao(dto.getDataEmissao());
        pagamento.setDataVencimento(dto.getDataVencimento());
        pagamento.setNomeFornecedor(dto.getNomeFornecedor());
        pagamento.setObservacao(dto.getObservacao());
        pagamento.setDesconto(dto.getDesconto());
        pagamento.setValorBruto(dto.getValorBruto());
        pagamento.setValorLiquido(dto.getValorLiquido());
        pagamento.setTipoPedido(tipoPedido);

        return repository.save(pagamento);
    }

    @GetMapping
    public List<Pagamento> pesquisar(
        @RequestParam(value = "nomePedido", required = false, defaultValue = "") String nomePedido,
        @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor
    ) {
        return repository.findByNomeFornecedorAndNomePedido( "%" + nomeForcenedor + "%", "%" + nomePedido + "%");
    }

    // metodo para achar um pagamento pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("/{id}")
    public Pagamento acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
