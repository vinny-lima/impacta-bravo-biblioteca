package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.Editora;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class EditoraResponse {
    private final Integer id;
    private final String razaoSocial;
    private final String nomeFantasia;
    private final String documento;
    private final String telefone;
    private final String email;
    private final String logradouro;
    private final Integer numero;
    private final String complemento;
    private final String bairro;
    private final String municipio;
    private final String uf;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private final LocalDate dataCriacao;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private final LocalDate dataAtualizacao;

    private final String cep;

    public EditoraResponse(Editora editora) {
        this.id = editora.getId();
        this.razaoSocial = editora.getRazaoSocial();
        this.nomeFantasia = editora.getNomeFantasia();
        this.documento = editora.getDocumento();
        this.telefone = editora.getTelefone();
        this.email = editora.getEmail();
        this.logradouro = editora.getLogradouro();
        this.numero = editora.getNumero();
        this.complemento = editora.getComplemento();
        this.bairro = editora.getBairro();
        this.municipio = editora.getMunicipio();
        this.uf = editora.getUf();
        this.dataCriacao = editora.getDataCriacao();
        this.dataAtualizacao = editora.getDataAtualizacao();
        this.cep = editora.getCep();
    }

    public Integer getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getUf() {
        return uf;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public String getCep() {return cep;}
}
