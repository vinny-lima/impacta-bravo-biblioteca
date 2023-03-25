package com.biblioteca.resource.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LivroRequest {

    @NotBlank(message = "Campo titulo é obrigatório")
    private String titulo;
    @NotBlank(message = "Campo subtitulo é obrigatório")
    private String subtitulo;
    @NotBlank(message = "Campo descricao é obrigatório")
    private String descricao;
    @NotNull(message = "Campo paginas é obrigatório")
    private Integer paginas;
    @NotBlank(message = "Campo isbn é obrigatório")
    private String isbn;

    @NotNull(message = "Campo quantidade é obrigatório")
    private Integer quantidade;
    @NotNull(message = "Campo editoraId é obrigatório")
    private Integer editoraId;

    public LivroRequest(String titulo, String subtitulo, String descricao,
                        Integer paginas, String isbn, Integer quantidade,Integer editoraId) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descricao = descricao;
        this.paginas = paginas;
        this.isbn = isbn;
        this.editoraId = editoraId;
        this.quantidade = quantidade;
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
}
