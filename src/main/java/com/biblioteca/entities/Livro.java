package com.biblioteca.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private Integer paginas;
    private String isbn;
    @ManyToOne
    private Editora editora;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    public Livro() {}

    public Livro(Integer id, String titulo, String subtitulo, String descricao, Integer paginas,
                 String isbn, Editora editora, LocalDate dataCriacao, LocalDate dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descricao = descricao;
        this.paginas = paginas;
        this.isbn = isbn;
        this.editora = editora;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
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
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) && Objects.equals(titulo, livro.titulo)
                && Objects.equals(subtitulo, livro.subtitulo)
                && Objects.equals(descricao, livro.descricao)
                && Objects.equals(paginas, livro.paginas)
                && Objects.equals(isbn, livro.isbn)
                && Objects.equals(editora, livro.editora)
                && Objects.equals(dataCriacao, livro.dataCriacao)
                && Objects.equals(dataAtualizacao, livro.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, subtitulo, descricao, paginas, isbn,
                editora, dataCriacao, dataAtualizacao);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", paginas=" + paginas +
                ", isbn='" + isbn + '\'' +
                ", editora=" + editora +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
