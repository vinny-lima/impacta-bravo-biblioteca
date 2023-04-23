package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AutorRequest {

    @NotBlank(message = "Campo nome é obrigatório")
    private final String nome;
    @NotBlank(message = "Campo nomeFantasia é obrigatório")
    private final String nomeFantasia;

    public AutorRequest(String nome, String nomeFantasia) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    @Override
    public String toString() {
        return "AutorRequest{" +
                "nome='" + nome + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                '}';
    }
}
