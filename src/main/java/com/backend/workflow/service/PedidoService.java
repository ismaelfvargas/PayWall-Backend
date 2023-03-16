package com.backend.workflow.service;

import com.backend.workflow.entity.Pedido;
import com.backend.workflow.exception.NotFoundException;
import com.backend.workflow.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository repository;

    public Pedido get(Integer id){
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

}
