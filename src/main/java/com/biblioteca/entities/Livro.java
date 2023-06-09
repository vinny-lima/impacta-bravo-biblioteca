package com.biblioteca.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String subtitulo;
    @Column(columnDefinition = "LONGTEXT")
    private String descricao;
    private Integer paginas;
    private String isbn;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "livros_autores",
            joinColumns = @JoinColumn(name = "id_livro", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_autor", referencedColumnName = "id")
    )
    private List<Autor> autores;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "livros_generos",
            joinColumns = @JoinColumn(name = "id_livro", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_genero", referencedColumnName = "id")
    )
    private List<GeneroLiterario> generosLiterarios;

    public Livro() {}

    public Livro(Integer id, String titulo, String subtitulo, String descricao,
                 Integer paginas, String isbn, Editora editora, Integer quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descricao = descricao;
        this.paginas = paginas;
        this.isbn = isbn;
        this.editora = editora;
        this.quantidade = quantidade;
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

    public Integer getQuantidade() {return quantidade;}

    public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}

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

    public List<Autor> getAutores() {
        if (autores == null) autores = new ArrayList<>();
        return autores;
    }

    public void setAutores(List<Autor> autores) {this.autores = autores;}

    public void adicionarAutores(List<Autor> listAutores){
        if (listAutores != null){
            for (Autor autor : listAutores){
                adicionarAutor(autor);
            }
        }
    }

    private void adicionarAutor(Autor autor){
        if (autor != null && !getAutores().contains(autor)){
            getAutores().add(autor);
            if (!autor.getLivros().contains(this)) autor.getLivros().add(this);
        }
    }

    public void removerAutores(){
        if (!getAutores().isEmpty()){
            for (Autor autor : getAutores()){
                removerAutor(autor);
            }
        }
    }

    private void removerAutor(Autor autor){
        if (autor != null && getAutores().contains(autor)){
            getAutores().remove(autor);
            if (autor.getLivros().contains(this)) autor.getLivros().remove(this);
        }
    }

    public List<GeneroLiterario> getGenerosLiterarios() {
        if (generosLiterarios == null) generosLiterarios = new ArrayList<>();
        return generosLiterarios;
    }

    public void setGenerosLiterarios(List<GeneroLiterario> generosLiterarios) {this.generosLiterarios = generosLiterarios;}

    public void adicionarGenerosLiterarios(List<GeneroLiterario> generoLiterario){
        if (generoLiterario != null){
            for (GeneroLiterario genero : generoLiterario){
                adicionarGeneroLiterario(genero);
            }
        }
    }

    private void adicionarGeneroLiterario(GeneroLiterario generoLiterario){
        if (generoLiterario != null && !getGenerosLiterarios().contains(generoLiterario)){
            getGenerosLiterarios().add(generoLiterario);
            if (!generoLiterario.getLivros().contains(this)) generoLiterario.getLivros().add(this);
        }
    }

    public void removerGenerosLiterarios(){
        if (!getGenerosLiterarios().isEmpty()){
            for (GeneroLiterario generoLiterario : getGenerosLiterarios()){
                removerGeneroLiterario(generoLiterario);
            }
        }
    }

    private void removerGeneroLiterario(GeneroLiterario generoLiterario){
        if (generoLiterario != null && getGenerosLiterarios().contains(generoLiterario)){
            getGenerosLiterarios().remove(generoLiterario);
            if (generoLiterario.getLivros().contains(this)) generoLiterario.getLivros().remove(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id)
                && Objects.equals(titulo, livro.titulo)
                && Objects.equals(subtitulo, livro.subtitulo)
                && Objects.equals(descricao, livro.descricao)
                && Objects.equals(paginas, livro.paginas)
                && Objects.equals(isbn, livro.isbn)
                && Objects.equals(quantidade, livro.quantidade)
                && Objects.equals(editora, livro.editora)
                && Objects.equals(dataCriacao, livro.dataCriacao)
                && Objects.equals(dataAtualizacao, livro.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, subtitulo, descricao, paginas,
                isbn, quantidade, editora, dataCriacao, dataAtualizacao);
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
                ", quantidade=" + quantidade +
                ", editora=" + editora +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
