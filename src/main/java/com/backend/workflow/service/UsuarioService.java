package com.backend.workflow.service;

import com.backend.workflow.entity.Usuario;
import com.backend.workflow.exception.RegisteredUserException;
import com.backend.workflow.exception.UsernameNotFoundException;
import com.backend.workflow.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario salvar(Usuario usuario){
        boolean exists = repository.existsByEmail(usuario.getEmail());
        String login = usuario.getEmail();
        if(exists){
            throw new RegisteredUserException(login);
        }
        return repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository
                            .findByEmail(email)
                            .orElseThrow(UsernameNotFoundException::new);
        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getCargo().getRoles())
                .build()
                ;
    }
}
