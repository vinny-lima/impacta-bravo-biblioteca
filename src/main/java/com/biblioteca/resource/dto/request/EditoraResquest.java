package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditoraResquest {

    @NotBlank(message = "Campo razao social é obrigatório")
    private String razaoSocial;
    @NotBlank(message = "Campo nome fantasia é obrigatório")
    private String nomeFantasia;
    @NotBlank(message = "Campo documento é obrigatório")
    private String documento;
    @NotBlank(message = "Campo telefone é obrigatório")
    private String telefone;
    @NotBlank(message = "Campo email é obrigatório")
    private String email;
    @NotBlank(message = "Campo logradouro é obrigatório")
    private String logradouro;
    @NotNull(message = "Campo numero é obrigatório")
    private Integer numero;
    @NotBlank(message = "Campo complemento é obrigatório")
    private String complemento;
    @NotBlank(message = "Campo bairro é obrigatório")
    private String bairro;
    @NotBlank(message = "Campo municipio é obrigatório")
    private String municipio;
    @NotBlank(message = "Campo uf é obrigatório")
    private String uf;

    public EditoraResquest(String razaoSocial, String nomeFantasia, String documento,
                           String telefone, String email, String logradouro, Integer numero,
                           String complemento, String bairro, String municipio, String uf) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.documento = documento;
        this.telefone = telefone;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
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
}
