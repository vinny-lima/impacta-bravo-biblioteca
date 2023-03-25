package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class LivroResponse {

    private Integer id;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private Integer paginas;
    private String isbn;
    private Integer quantidade;
    private Editora editora;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataCriacao;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dataAtualizacao;

    public LivroResponse(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.subtitulo = livro.getSubtitulo();
        this.descricao = livro.getDescricao();
        this.paginas = livro.getPaginas();
        this.isbn = livro.getIsbn();
        this.quantidade = livro.getQuantidade();
        this.editora = livro.getEditora();
        this.dataCriacao = livro.getDataCriacao();
        this.dataAtualizacao = livro.getDataAtualizacao();
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

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }
}
