package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;

public class GeneroLiterarioRequest{

    @NotBlank(message = "Campo nome é obrigatório")
    private String descricao;

    public GeneroLiterarioRequest(String descricao) {
        this.descricao = descricao;
    }

    public GeneroLiterarioRequest() {}

    public String getDescricao() {return descricao;}

    @Override
    public String toString() {
        return "GeneroLiterarioRequest{" +
                "descricao='" + descricao + '\'' +
                '}';
    }
}
