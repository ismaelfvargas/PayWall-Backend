package com.backend.workflow.useCase;

import com.backend.workflow.dto.UsuarioDTO;
import com.backend.workflow.entity.Cargo;
import com.backend.workflow.entity.Usuario;
import com.backend.workflow.enums.PerfisFuncoesEnum;
import com.backend.workflow.exception.NotAuthorizedException;
import com.backend.workflow.message.MessagesComponent;
import com.backend.workflow.repository.UsuarioRepository;
import com.backend.workflow.service.CargoService;
import com.backend.workflow.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UsuarioUseCase {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;

    public void salvar(UsuarioDTO dto){
            permissaoCriarUsuario(dto);
            service.salvar(criarUsuario(dto));
    }

    private Usuario criarUsuario(UsuarioDTO dto){
        Usuario usuario = new Usuario();
        Cargo cargo = cargoService.get(dto.getCargoId());
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(dto.getPassword());
        usuario.setPassword(senhaCriptografada);
        usuario.setUsername(dto.getUsername());
        usuario.setCargo(cargo);
        return usuario;
    }

    private void permissaoCriarUsuario(UsuarioDTO dto){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow( () -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        String usuarioCargo = usuario.getCargo().getRoles();
        String usuarioRole = usuario.getCargo().getArea();

        Cargo cargo = cargoService.get(dto.getCargoId());
        String role = cargo.getArea();

        if (!Objects.equals(usuarioCargo, PerfisFuncoesEnum.MANAGER.getId()) && (!Objects.equals(usuarioCargo, PerfisFuncoesEnum.ADMINISTRATIVE.getId()))){
            throw new NotAuthorizedException();
        }
        if((!Objects.equals(usuarioRole, role)) && (!Objects.equals(usuarioRole, PerfisFuncoesEnum.ADMINISTRATIVE.getId()))){
            throw new NotAuthorizedException(MessagesComponent.get("search.NotAuthorized.exception.for.the.area"));
        }
    }

    public void permissao(){
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

        String adminRole = usuario.getCargo().getRoles();
        String gerente = "GERENTE";
        String coordenador = "COORDENADOR";
        String diretor = "DIRETOR";

        if( (!Objects.equals(new String(adminRole), new String(gerente))) &&
                (!Objects.equals(new String(adminRole), new String(coordenador))) &&
                (!Objects.equals(new String(adminRole), new String(diretor)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void permissaoAssistente(){

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

        String adminRole = usuario.getCargo().getRoles();
        String assistente = "ASSISTENTE";

        if( (!Objects.equals(new String(adminRole), new String(assistente)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void permissaoAdministrador(){

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

        String adminRole = usuario.getCargo().getRoles();
        String administrador = "ADMINISTRADOR";

        if( (!Objects.equals(new String(adminRole), new String(administrador)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
