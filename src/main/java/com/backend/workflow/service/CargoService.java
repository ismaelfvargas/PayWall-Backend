package com.backend.workflow.service;

import com.backend.workflow.entity.Cargo;
import com.backend.workflow.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CargoService {

    private final CargoRepository repository;

    public Cargo get(Long id){
        return repository.findById(id).get();
    }

    public Cargo findByArea(String area){
        return repository.findByArea(area).get();
    }
}
