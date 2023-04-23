package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditoraRequest {

    @NotBlank(message = "Campo razao social é obrigatório")
    private final String razaoSocial;
    @NotBlank(message = "Campo nome fantasia é obrigatório")
    private final String nomeFantasia;
    @NotBlank(message = "Campo documento é obrigatório")
    private final String documento;
    @NotBlank(message = "Campo telefone é obrigatório")
    private final String telefone;
    @NotBlank(message = "Campo email é obrigatório")
    private final String email;
    @NotBlank(message = "Campo logradouro é obrigatório")
    private final String logradouro;
    @NotNull(message = "Campo numero é obrigatório")
    private final Integer numero;
    @NotBlank(message = "Campo complemento é obrigatório")
    private final String complemento;
    @NotBlank(message = "Campo bairro é obrigatório")
    private final String bairro;
    @NotBlank(message = "Campo municipio é obrigatório")
    private final String municipio;
    @NotBlank(message = "Campo uf é obrigatório")
    private final String uf;
    @NotBlank(message = "Campo cep é obrigatório")
    private final String cep;

    public EditoraRequest(String razaoSocial, String nomeFantasia,
                          String documento, String telefone, String email,
                          String logradouro, Integer numero, String complemento,
                          String bairro, String municipio, String uf, String cep) {
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
        this.cep = cep;
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

    public String getCep() {return cep;}

    @Override
    public String toString() {
        return "EditoraRequest{" +
                "razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", documento='" + documento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero=" + numero +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", municipio='" + municipio + '\'' +
                ", uf='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
