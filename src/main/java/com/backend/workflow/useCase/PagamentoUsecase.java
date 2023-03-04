package com.backend.workflow.useCase;

import com.backend.workflow.dto.PagamentoDTO;
import com.backend.workflow.entity.*;
import com.backend.workflow.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PagamentoUsecase {

    private final PagamentoRepository repository;
    private final TipoPedidoRepository tipoPedidoRepository;
    private final TipoStatusRepository tipoStatusRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoStatusAdtoRepository tipoStatusAdtoRepository;

    public Pagamento salvar (PagamentoDTO dto){

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

    public List<Pagamento> pesquisar(String nomeFornecedor, String nomeStatus) {

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

        if (Objects.equals(new String("USUARIO"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeFornecedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("GERENTE"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteAndNotDiretor( "%" + nomeFornecedor + "%",   "%" + nomeStatus + "%", usuario.getCargo().getArea());
        }else if (Objects.equals(new String("COORDENADOR"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndAreaAndNotGerenteNotCoordenadorNotDiretor( "%" + nomeFornecedor + "%",   "%" + nomeStatus + "%", usuario.getCargo().getArea());
        }

        return repository.findByNomeFornecedorAndNomeStatus( "%" + nomeFornecedor + "%",   "%" + nomeStatus + "%");
    }

    public void atualizar(Integer id, Pagamento pagamentoAtualizado) {
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

    public List<Pagamento> pesquisarMeusPedidos(String nomeForcenedor, String nomeStatus){

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

        if (Objects.equals(new String("USUARIO"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("GERENTE"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("COORDENADOR"), new String(usuario.getCargo().getRoles()))){
            return repository.findByNomeFornecedorAndNomeStatusAndUserId( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("ASSISTENTE"), new String(usuario.getCargo().getRoles()))) {
            return repository.findByNomeFornecedorAndNomeStatusAndUserId("%" + nomeForcenedor + "%", "%" + nomeStatus + "%", usuario.getId());
        }else if (Objects.equals(new String("DIRETOR"), new String(usuario.getCargo().getRoles()))) {
            return repository.findByNomeFornecedorAndNomeStatusAndUserId("%" + nomeForcenedor + "%", "%" + nomeStatus + "%", usuario.getId());
        }
        return repository.findByNomeFornecedorAndNomeStatus( "%" + nomeForcenedor + "%",   "%" + nomeStatus + "%");
    }

    public void inativarPagamento(Integer id){
        repository
                .findById(id)
                .map( pagamento -> {
                    repository.delete(pagamento);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado") );
    }
}
