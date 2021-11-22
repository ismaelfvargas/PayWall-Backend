package com.backend.workflow.controller;


import com.backend.workflow.dto.PagamentoDTO;
import com.backend.workflow.entity.*;
import com.backend.workflow.repository.*;
import com.backend.workflow.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    private final TipoStatusAdtoRepository tipoStatusAdtoRepository;


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
//        Integer idTipoStatus = dto.getIdTipoStatus();

        TipoPedido tipoPedido = tipoPedidoRepository.findById(idTipoPedido).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Tipo do pedido inexistente"
                )
        );

        TipoStatus tipoStatus = tipoStatusRepository.findById(1).orElseThrow(() ->
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

        if (idTipoPedido == 2) {
            TipoStatusAdto tipoStatusAdto = tipoStatusAdtoRepository.findById(1).orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Tipo do status inexistente"
                    )
            );
            pagamento.setTipoStatusAdto(tipoStatusAdto);
        }

        return repository.save(pagamento);
    }

    @GetMapping
    public List<Pagamento> pesquisar(
        @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor,
        @RequestParam(value = "nomeStatus", required = false, defaultValue = "") String nomeStatus
    ) {

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

        if (Objects.equals(new String("USUARIO"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("GERENTE"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteAndNotDiretor( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getArea());
        }else if (Objects.equals(new String("COORDENADOR"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteNotCoordenadorNotDiretor( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getArea());
        }

        return repository.findByNomeFornecedorAndNomeStatus( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%");
    }



    // metodo para achar um pagamento pelo ID, depois exception para caso não exista o ID (Postman)
    @GetMapping("{id}")
    public Pagamento acharPorId(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void atualizar(@PathVariable Integer id, @RequestBody Pagamento pagamentoAtualizado){
        repository.findById(id)
                .map(pagamento -> {
                    pagamento.setTipoStatus(pagamentoAtualizado.getTipoStatus());
                    pagamento.setTributo(pagamentoAtualizado.getTributo());
                    pagamento.setDataEmissao(pagamentoAtualizado.getDataEmissao());
                    pagamento.setDataVencimento(pagamentoAtualizado.getDataVencimento());
                    pagamento.setNomeFornecedor(pagamentoAtualizado.getNomeFornecedor());
                    pagamento.setObservacao(pagamentoAtualizado.getObservacao());
                    pagamento.setDesconto(pagamentoAtualizado.getDesconto());
                    pagamento.setValorBruto(pagamentoAtualizado.getValorBruto());
                    pagamento.setValorLiquido(pagamentoAtualizado.getValorLiquido());
                    pagamento.setCentroDeCusto(pagamentoAtualizado.getCentroDeCusto());
                    pagamento.setTipoPedido(pagamentoAtualizado.getTipoPedido());
                    pagamento.setTipoStatus(pagamentoAtualizado.getTipoStatus());
                    return repository.save(pagamentoAtualizado);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    public List<Pagamento> pesquisarMeusPedidos(
            @RequestParam(value = "nomeFornecedor", required = false, defaultValue = "") String nomeForcenedor,
            @RequestParam(value = "nomeStatus", required = false, defaultValue = "") String nomeStatus
    ) {

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

        if (Objects.equals(new String("USUARIO"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("GERENTE"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("COORDENADOR"), new String(usuario.getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("ASSISTENTE"), new String(usuario.getRoles()))) {
            return repository.findByNomeFornecedorAndNomeStatusAndUserId("%" + nomeForcenedor + "%", "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("DIRETOR"), new String(usuario.getRoles()))) {
                return repository.findByNomeFornecedorAndNomeStatusAndUserId("%" + nomeForcenedor + "%", "%" + nomeStatus + "%", usuario.getId());
        }
                return repository.findByNomeFornecedorAndNomeStatus( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%");
    }


}
