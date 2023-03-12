package com.backend.workflow.controller;

import com.backend.workflow.entity.Cargo;
import com.backend.workflow.useCase.CargoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class CargoController {

    private final CargoUseCase useCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cargo> salvar(@RequestBody @Valid Cargo cargo){
       return useCase.salvar(cargo);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Cargo> buscarId(@PathVariable Long id){
        return useCase.buscarId(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Cargo> listar(){
        return useCase.listar();
    }
}
