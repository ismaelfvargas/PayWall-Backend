package com.backend.workflow.useCase;

import com.backend.workflow.entity.Cargo;
import com.backend.workflow.entity.Usuario;
import com.backend.workflow.enums.PerfisFuncoesEnum;
import com.backend.workflow.exception.NotAcceptableException;
import com.backend.workflow.exception.RegisteredUserException;
import com.backend.workflow.exception.RoleException;
import com.backend.workflow.repository.UsuarioRepository;
import com.backend.workflow.service.CargoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@Service
public class CargoUseCase {

    private final CargoService service;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity <Cargo> salvar(Cargo cargo){
        permissaoCriarCargo(cargo);
        return ResponseEntity.ok(service.save(cargo));
    }

    private void permissaoCriarCargo(Cargo cargo){

        boolean existsRole = service.existsByRoles(cargo.getRoles());
        boolean existsArea = service.existsByArea(cargo.getArea());
        String roles = cargo.getRoles();
        if(Objects.equals(existsArea, existsRole)){
            throw new RoleException(roles);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = "";
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow( () -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        String usuarioCargo = usuario.getCargo().getRoles();
        String usuarioRole = usuario.getCargo().getArea();

        if (!Objects.equals(usuarioCargo, PerfisFuncoesEnum.ADMINISTRATIVE.getId()) || (!Objects.equals(usuarioRole, PerfisFuncoesEnum.ADMINISTRATIVE.getId()))){
            throw new NotAcceptableException();
        }
    }

    public ResponseEntity<Cargo> buscarId(Long id){
       return ResponseEntity.ok(service.get(id));
    }

    public List<Cargo> listar(){
        return service.findAll();
    }
}
