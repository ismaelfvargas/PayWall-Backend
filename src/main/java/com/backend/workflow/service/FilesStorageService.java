package com.backend.workflow.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

// Interface que inicializa o armazenamento, salvar um novo arquivo, carregar o arquivo, obter a lista de informações dos arquivos, excluir todos os arquivos.
public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}

