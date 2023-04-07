package com.biblioteca.resource.exception;

import com.biblioteca.service.exceptions.EditoraIntegridadeDadosException;
import com.biblioteca.service.exceptions.EditoraJaCadastradaException;
import com.biblioteca.service.exceptions.EditoraNaoEncontradaException;
import com.biblioteca.service.exceptions.EditoraNullException;
import com.biblioteca.service.exceptions.LivroIntegridadeDadosException;
import com.biblioteca.service.exceptions.LivroJaCadastradoException;
import com.biblioteca.service.exceptions.LivroNaoEncontradoException;
import com.biblioteca.service.exceptions.LivroNullException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(value = {EditoraIntegridadeDadosException.class, LivroIntegridadeDadosException.class})
    public ResponseEntity<ErroPadrao> objetoIntegridadeDeDados(Exception e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Integridade de dados.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(value = {EditoraNullException.class, LivroNullException.class})
    public ResponseEntity<ErroPadrao> objetoNull(Exception e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Body Request null.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(value = {EditoraJaCadastradaException.class, LivroJaCadastradoException.class})
    public ResponseEntity<ErroPadrao> objetoJaCadastrado(Exception e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Já cadastrado.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(value = {EditoraNaoEncontradaException.class, LivroNaoEncontradoException.class})
    public ResponseEntity<ErroPadrao> objetoNaoEncontrado(Exception e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Não encontrado.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validacaoDeCampos(MethodArgumentNotValidException e, HttpServletRequest request) {

        //passando para o super()
        ErroValidacao err = new ErroValidacao(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação", e.getMessage(), request.getRequestURI());

        /*Percorrendo a lista de erro
         * e adicionando a lista campo do erro
         * e a msg default dele.
         */
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addErro(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}
