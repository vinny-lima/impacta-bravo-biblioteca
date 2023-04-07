package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class LivroRequest {

    @NotBlank(message = "Campo titulo é obrigatório")
    private final String titulo;
    @NotBlank(message = "Campo subtitulo é obrigatório")
    private final String subtitulo;
    @NotBlank(message = "Campo descricao é obrigatório")
    private final String descricao;
    @NotNull(message = "Campo paginas é obrigatório")
    private final Integer paginas;
    @NotBlank(message = "Campo isbn é obrigatório")
    private final String isbn;

    @NotNull(message = "Campo quantidade é obrigatório")
    private final Integer quantidade;
    @NotNull(message = "Campo editoraId é obrigatório")
    private final Integer editoraId;
    @NotEmpty(message = "Campo autoresId é obrigatório")
    private final List<Integer> autoresId;

    public LivroRequest(String titulo, String subtitulo, String descricao,
                        Integer paginas, String isbn, Integer quantidade,
                        Integer editoraId, List<Integer> autoresId) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descricao = descricao;
        this.paginas = paginas;
        this.isbn = isbn;
        this.quantidade = quantidade;
        this.editoraId = editoraId;
        this.autoresId = autoresId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getQuantidade() {return quantidade;}

    public Integer getEditoraId() {
        return editoraId;
    }

    public List<Integer> getAutoresId() {return autoresId;}

    @Override
    public String toString() {
        return "LivroRequest{" +
                "titulo='" + titulo + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", paginas=" + paginas +
                ", isbn='" + isbn + '\'' +
                ", quantidade=" + quantidade +
                ", editoraId=" + editoraId +
                ", autoresId=" + autoresId.toString() +
                '}';
    }
}
