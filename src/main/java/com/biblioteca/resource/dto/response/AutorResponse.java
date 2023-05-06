package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.Autor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AutorResponse implements Serializable {

    private final Integer id;
    private final String nome;
    private final String nomeFantasia;
    private final LocalDate dataCriacao;
    private final LocalDate dataAtualizacao;

    public AutorResponse(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.nomeFantasia = autor.getNomeFantasia();
        this.dataCriacao = autor.getDataCriacao();
        this.dataAtualizacao = autor.getDataAtualizacao();
    }

    public static List<AutorResponse> toListaAutorResponse(List<Autor> listaAutor){
        return listaAutor.stream().map(AutorResponse::new).toList();
    }

    public Integer getId() {return id;}

    public String getNome() {return nome;}

    public String getNomeFantasia() {return nomeFantasia;}

    public LocalDate getDataCriacao() {return dataCriacao;}

    public LocalDate getDataAtualizacao() {return dataAtualizacao;}
}
