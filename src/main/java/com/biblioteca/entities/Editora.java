package com.biblioteca.entities;

import com.biblioteca.resource.dto.request.EditoraResquest;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "nome_fantasia")
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
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    public Editora() {}

    public Editora(Integer id, String razaoSocial, String nomeFantasia,
                   String documento, String telefone, String email,
                   String logradouro, Integer numero, String complemento,
                   String bairro, String municipio, String uf) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editora editora = (Editora) o;
        return Objects.equals(id, editora.id)
                && Objects.equals(razaoSocial, editora.razaoSocial)
                && Objects.equals(nomeFantasia, editora.nomeFantasia)
                && Objects.equals(documento, editora.documento)
                && Objects.equals(telefone, editora.telefone)
                && Objects.equals(email, editora.email)
                && Objects.equals(logradouro, editora.logradouro)
                && Objects.equals(numero, editora.numero)
                && Objects.equals(complemento, editora.complemento)
                && Objects.equals(bairro, editora.bairro)
                && Objects.equals(municipio, editora.municipio)
                && Objects.equals(uf, editora.uf)
                && Objects.equals(dataCriacao, editora.dataCriacao)
                && Objects.equals(dataAtualizacao, editora.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, razaoSocial, nomeFantasia, documento, telefone, email,
                logradouro, numero, complemento, bairro, municipio, uf, dataCriacao, dataAtualizacao);
    }

    @Override
    public String toString() {
        return "Editora{" +
                "id=" + id +
                ", razaoSocial='" + razaoSocial + '\'' +
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
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
