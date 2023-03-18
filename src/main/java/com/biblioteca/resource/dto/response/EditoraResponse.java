package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.Editora;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class EditoraResponse {

    private Integer id;
    private String razaoSocial;
    private String nomeFantasia;
    private String documento;
    private String telefone;
    private String email;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String municipio;
    private String uf;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataCriacao;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataAtualizacao;

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
}
