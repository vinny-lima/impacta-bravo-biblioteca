package com.biblioteca.entities;


import com.biblioteca.resource.dto.response.GeneroLiterarioResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class GeneroLiterario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    @ManyToMany(mappedBy = "generosLiterarios")
    private List<Livro> livros = new ArrayList<>();

    public GeneroLiterario() {}

    public GeneroLiterario(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public GeneroLiterario(GeneroLiterarioResponse generoLiterarioResponse) {
        this.id = generoLiterarioResponse.getId();
        this.descricao = generoLiterarioResponse.getDescricao();
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public List<Livro> getLivros() {return livros;}

    public void setLivros(List<Livro> livros) {this.livros = livros;}

    public static List<GeneroLiterario> toListaGeneroLiterario(List<GeneroLiterarioResponse> listaGeneroLiterarioResponse){
        return listaGeneroLiterarioResponse.stream().map(GeneroLiterario::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneroLiterario that = (GeneroLiterario) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao);
    }

    @Override
    public String toString() {
        return "GeneroLiterario{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
