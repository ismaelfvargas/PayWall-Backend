package com.backend.workflow.controller;


import com.backend.workflow.dto.PagamentoDTO;
import com.backend.workflow.entity.Pagamento;
import com.backend.workflow.entity.TipoPedido;
import com.backend.workflow.entity.TipoStatus;
import com.backend.workflow.entity.Usuario;
import com.backend.workflow.repository.PagamentoRepository;
import com.backend.workflow.repository.TipoPedidoRepository;
import com.backend.workflow.repository.TipoStatusRepository;
import com.backend.workflow.repository.UsuarioRepository;
import com.backend.workflow.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoRepository repository;
    private final TipoPedidoRepository tipoPedidoRepository;
    private final TipoStatusRepository tipoStatusRepository;
    private final BigDecimalConverter bigDecimalConverter;
    private final UsuarioRepository usuarioRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento salvar(@RequestBody PagamentoDTO dto){

        // TODO: Passar para uma função global/compartilhada
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        //

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow( () -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        Integer idTipoPedido = dto.getIdTipoPedido();
        Integer idTipoStatus = dto.getIdTipoStatus();

        TipoPedido tipoPedido = tipoPedidoRepository.findById(idTipoPedido).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Tipo do pedido inexistente"
                )
        );

        TipoStatus tipoStatus = tipoStatusRepository.findById(idTipoStatus).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Tipo do status inexistente"
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
        pagamento.setCentroDeCusto(dto.getCentroDeCusto());
        pagamento.setTipoPedido(tipoPedido);
        pagamento.setTipoStatus(tipoStatus);
        pagamento.setUsuario(usuario);

        return repository.save(pagamento);
    }

    @GetMapping
    public List<Pagamento> pesquisar(
//        @RequestParam(value = "nomePedido", required = false, defaultValue = "") String nomePedido,
        @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor,
        @RequestParam(value = "nomeStatus", required = false, defaultValue = "") String nomeStatus
    ) {
//        return repository.findByNomeFornecedorAndNomePedido( "%" + nomeForcenedor + "%", "%" + nomePedido + "%", "%" + nomeStatus + "%");
        return repository.findByNomeFornecedorAndNomePedido( "%" + nomeForcenedor + "%", "%" + nomeStatus + "%");
    }

    // metodo para achar um pagamento pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("/{id}")
    public Pagamento acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
