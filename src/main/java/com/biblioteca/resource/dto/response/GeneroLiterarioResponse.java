package com.biblioteca.resource.dto.response;

import com.biblioteca.entities.GeneroLiterario;

import java.io.Serializable;
import java.util.List;

public class GeneroLiterarioResponse implements Serializable {

    private final Integer id;
    private final String descricao;

    public GeneroLiterarioResponse(GeneroLiterario generoLiterario) {
        this.id = generoLiterario.getId();
        this.descricao = generoLiterario.getDescricao();
    }

    public static List<GeneroLiterarioResponse> toListaGeneroLiterarioResponse(List<GeneroLiterario> listaGeneroLiterario){
        return listaGeneroLiterario.stream().map(GeneroLiterarioResponse::new).toList();
    }

    public Integer getId() {return id;}

    public String getDescricao() {return descricao;}
}
