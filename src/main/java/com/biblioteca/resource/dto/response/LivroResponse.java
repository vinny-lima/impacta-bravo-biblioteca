package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static com.biblioteca.resource.dto.response.AutorResponse.toListaAutorResponse;
import static com.biblioteca.resource.dto.response.GeneroLiterarioResponse.toListaGeneroLiterarioResponse;

public class LivroResponse implements Serializable {

    private final Integer id;
    private final String titulo;
    private final String subtitulo;
    private final String descricao;
    private final Integer paginas;
    private final String isbn;
    private final Integer quantidade;
    private final Editora editora;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private final LocalDate dataCriacao;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private final LocalDate dataAtualizacao;

    private final List<AutorResponse> autores;

    private final List<GeneroLiterarioResponse> generosLiterarios;

    public LivroResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.subtitulo = livro.getSubtitulo();
        this.descricao = livro.getDescricao();
        this.paginas = livro.getPaginas();
        this.isbn = livro.getIsbn();
        this.quantidade = livro.getQuantidade();
        this.dataCriacao = livro.getDataCriacao();
        this.dataAtualizacao = livro.getDataAtualizacao();
        this.editora = livro.getEditora();
        this.autores = toListaAutorResponse(livro.getAutores());
        this.generosLiterarios = toListaGeneroLiterarioResponse(livro.getGenerosLiterarios());
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getQuantidade() {return quantidade;}

    public Editora getEditora() {
        return editora;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataAtualizacao() {return dataAtualizacao;}

    public List<AutorResponse> getAutores() {return autores;}

    public List<GeneroLiterarioResponse> getGenerosLiterarios() {return generosLiterarios;}
}
