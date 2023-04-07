package com.biblioteca.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ErroValidacao extends ErroPadrao {

    private static final long serialVersionUID = 1L;

    private List<CampoMensagem> errors = new ArrayList<>();

    public ErroValidacao(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<CampoMensagem> getErrors() {
        return errors;
    }

    /*Metodo Para adicionar
     *na lista obj CampoMensagem
     *passando os campos necessarios.
     */
    public void addErro(String fieldName, String message) {
        errors.add(new CampoMensagem(fieldName, message));
    }

}
