package com.biblioteca.resource.exception;

import java.io.Serializable;

public class CampoMensagem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String NomeCampo;

    private String mensagem;

    public CampoMensagem() {
    }

    public CampoMensagem(String fieldName, String message) {
        super();
        this.NomeCampo = fieldName;
        this.mensagem = message;
    }

    public String getNomeCampo() {
        return NomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.NomeCampo = nomeCampo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
