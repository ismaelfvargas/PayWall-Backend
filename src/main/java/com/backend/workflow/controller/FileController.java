package com.backend.workflow.controller;

import com.backend.workflow.message.ResponseFile;
import com.backend.workflow.message.ResponseMessage;
import com.backend.workflow.repository.FileDBRepository;
import com.backend.workflow.service.FileStorageService;
import com.backend.workflow.useCase.FileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("")
@RequiredArgsConstructor
public class FileController {


    private final FileUseCase useCase;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, String idSolicitacao) {
       return useCase.uploadFile(file, idSolicitacao);
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
       return useCase.getListFiles();
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        return useCase.getFile(id);
    }

    @GetMapping("/buscarDocumento/{idPagamento}")
    public ResponseEntity<List<ResponseFile>> getDocumentos(@PathVariable Integer idPagamento) {
        return useCase.getDocumentos(idPagamento);
    }
}