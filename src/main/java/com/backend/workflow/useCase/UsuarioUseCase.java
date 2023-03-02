package com.backend.workflow.useCase;

import com.backend.workflow.entity.Usuario;
import com.backend.workflow.exception.UsuarioCadastradoException;
import com.backend.workflow.repository.UsuarioRepository;
import com.backend.workflow.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UsuarioUseCase {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;

    public void salvar(Usuario usuario){

        try {
            MessageDigest algoritmo;
            byte messageDigest[];
            StringBuilder hexString;
            algoritmo = MessageDigest.getInstance("MD5");  // 32 letras
            messageDigest = algoritmo.digest(usuario.getPassword().getBytes("UTF-8"));
            hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            usuario.setPassword(hexString.toString());
            service.salvar(usuario);
        } catch (UsuarioCadastradoException | NoSuchAlgorithmException | UnsupportedEncodingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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

        String adminRole = usuario.getRoles();
        String administrador = "ADMINISTRADOR";

        if( (!Objects.equals(new String(adminRole), new String(administrador)))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
