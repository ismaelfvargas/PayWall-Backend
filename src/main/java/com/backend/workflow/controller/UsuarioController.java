package com.backend.workflow.controller;

import com.backend.workflow.entity.Usuario;
import com.backend.workflow.exception.UsuarioCadastradoException;
import com.backend.workflow.repository.UsuarioRepository;
import com.backend.workflow.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        try{
            service.salvar(usuario);
        }catch (UsuarioCadastradoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/permissao/menu")
    @ResponseStatus(HttpStatus.ACCEPTED)
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

        String adminRole = usuario.getRoles();
        String gerente = "GERENTE";
        String coordenador = "COORDENADOR";
        String diretor = "DIRETOR";


        if( (!Objects.equals(new String(adminRole), new String(gerente))) &&
                (!Objects.equals(new String(adminRole), new String(coordenador))) &&
                    (!Objects.equals(new String(adminRole), new String(diretor)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/permissao/assistente")
    @ResponseStatus(HttpStatus.ACCEPTED)
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

        String adminRole = usuario.getRoles();
        String assistente = "ASSISTENTE";


        if( (!Objects.equals(new String(adminRole), new String(assistente)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
