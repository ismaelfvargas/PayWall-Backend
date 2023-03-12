package com.backend.workflow.useCase;

import com.backend.workflow.dto.PedidoDTO;
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
public class PedidoUsecase {

    private final PedidoRepository repository;
    private final TipoPedidoRepository tipoPedidoRepository;
    private final TipoStatusRepository tipoStatusRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoStatusAdtoRepository tipoStatusAdtoRepository;

    public Pedido salvar (PedidoDTO dto){

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

        Pedido pedido = new Pedido();
        pedido.setTributo(dto.getTributo());
        pedido.setDataCadastro(dto.getDataCadastro());
        pedido.setDataEmissao(dto.getDataEmissao());
        pedido.setDataVencimento(dto.getDataVencimento());
        pedido.setNomeFornecedor(dto.getNomeFornecedor());
        pedido.setObservacao(dto.getObservacao());
        pedido.setDesconto(dto.getDesconto());
        pedido.setValorBruto(dto.getValorBruto());
        pedido.setValorLiquido(dto.getValorLiquido());
        pedido.setCentroDeCusto(dto.getCentroDeCusto());
        pedido.setTipoPedido(tipoPedido);
        pedido.setTipoStatus(tipoStatus);
        pedido.setUsuario(usuario);

        if (idTipoPedido == 2) {
            TipoStatusAdto tipoStatusAdto = tipoStatusAdtoRepository.findById(1).orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Tipo do status inexistente"
                    )
            );
            pedido.setTipoStatusAdto(tipoStatusAdto);
        }

        return repository.save(pedido);
    }

    public List<Pedido> pesquisar(String nomeFornecedor, String nomeStatus) {

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

    public void atualizar(Integer id, Pedido pedidoAtualizado) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setTipoStatus(pedidoAtualizado.getTipoStatus());
                    pedido.setTributo(pedidoAtualizado.getTributo());
                    pedido.setDataEmissao(pedidoAtualizado.getDataEmissao());
                    pedido.setDataVencimento(pedidoAtualizado.getDataVencimento());
                    pedido.setNomeFornecedor(pedidoAtualizado.getNomeFornecedor());
                    pedido.setObservacao(pedidoAtualizado.getObservacao());
                    pedido.setDesconto(pedidoAtualizado.getDesconto());
                    pedido.setValorBruto(pedidoAtualizado.getValorBruto());
                    pedido.setValorLiquido(pedidoAtualizado.getValorLiquido());
                    pedido.setCentroDeCusto(pedidoAtualizado.getCentroDeCusto());
                    pedido.setTipoPedido(pedidoAtualizado.getTipoPedido());
                    pedido.setTipoStatus(pedidoAtualizado.getTipoStatus());
                    return repository.save(pedidoAtualizado);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Pedido> pesquisarMeusPedidos(String nomeForcenedor, String nomeStatus){

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

    public void inativarPedido(Integer id){
        repository
                .findById(id)
                .map( pedido -> {
                    repository.delete(pedido);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado") );
    }
}
