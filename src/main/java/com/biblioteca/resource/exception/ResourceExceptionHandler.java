package com.biblioteca.resource.exception;

import com.biblioteca.service.exceptions.EditoraJaCadastradaException;
import com.biblioteca.service.exceptions.EditoraNaoEncontradaException;
import com.biblioteca.service.exceptions.EditoraNullException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EditoraNullException.class)
    public ResponseEntity<ErroPadrao> editoraNull(EditoraNullException e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "EditoraRequest null", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(EditoraJaCadastradaException.class)
    public ResponseEntity<ErroPadrao> editoraJaCadastrada(EditoraJaCadastradaException e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Editora já cadastrada", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(EditoraNaoEncontradaException.class)
    public ResponseEntity<ErroPadrao> editoraNaoEncontrada(EditoraNaoEncontradaException e, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Editora não encontrada", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException e, HttpServletRequest request) {

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
