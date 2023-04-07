package com.biblioteca.entities;

import com.biblioteca.resource.dto.response.AutorResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String nomeFantasia;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    @ManyToMany(mappedBy = "autores")
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(Integer id, String nome, String nomeFantasia,
                 LocalDate dataCriacao, LocalDate dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Autor(AutorResponse autorResponse) {
        this.id = autorResponse.getId();
        this.nome = autorResponse.getNome();
        this.nomeFantasia = autorResponse.getNomeFantasia();
        this.dataCriacao = autorResponse.getDataCriacao();
        this.dataAtualizacao = autorResponse.getDataAtualizacao();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public static List<Autor> toListaAutor(List<AutorResponse> listaAutor){
        return listaAutor.stream().map(Autor::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autores = (Autor) o;
        return Objects.equals(id, autores.id) &&
                Objects.equals(nome, autores.nome) &&
                Objects.equals(nomeFantasia, autores.nomeFantasia) &&
                Objects.equals(dataCriacao, autores.dataCriacao) &&
                Objects.equals(dataAtualizacao, autores.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, nomeFantasia, dataCriacao, dataAtualizacao);
    }

    @Override
    public String toString() {
        return "Autores{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", livros=" + livros.toString() +
                '}';
    }
}
