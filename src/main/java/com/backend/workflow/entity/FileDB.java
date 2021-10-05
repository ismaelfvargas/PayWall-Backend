package com.backend.workflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @Column(name = "id_pagamento")
    private Integer idSolicitacao;

    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data, Integer idSolicitacao) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.idSolicitacao = idSolicitacao;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setIdSolicitacao(Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
