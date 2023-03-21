package com.biblioteca.service;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.resource.dto.request.LivroRequest;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.resource.dto.response.LivroResponse;
import com.biblioteca.service.exceptions.LivroJaCadastradoException;
import com.biblioteca.service.exceptions.LivroNaoEncontradoException;
import com.biblioteca.service.exceptions.LivroNullException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository repository;

    private final EditoraService editoraService;

    @Autowired
    public LivroService(LivroRepository repository, EditoraService editoraService) {
        this.repository = repository;
        this.editoraService = editoraService;
    }

    @Transactional(rollbackForClassName = {"LivroNullException", "LivroJaCadastradoException"})
    public LivroResponse salvar(LivroRequest dto){
        if (dto == null) throw new LivroNullException("LivroRequest dto é nulo.");

        Optional<Livro> optionalLivro = buscarLivroPorEditora(dto.getTitulo(), dto.getEditoraId());

        if (optionalLivro.isPresent())
            throw new LivroJaCadastradoException("Esse livro já está cadastrado.");

        EditoraResponse editoraResponse = editoraService.buscarPorId(dto.getEditoraId());
        Editora editora = editoraService.editoraResponseToEditora(editoraResponse);

        Livro livro = livroRequestToLivro(dto, editora);
        livro.setDataCriacao(LocalDate.now());
        livro.setDataAtualizacao(LocalDate.now());

        return new LivroResponse(repository.save(livro));
    }

    public LivroResponse buscarPorId(Integer id){

        if (id == null) throw new LivroNullException("Id nulo para buscar livro por id.");

        Optional<Livro> optionalLivro = repository.findById(id);

        if (optionalLivro.isEmpty()) throw new LivroNaoEncontradoException("Livro não encontrada com id: "+id);

        return new LivroResponse(optionalLivro.get());
    }

    @Transactional(rollbackForClassName = {"LivroNaoEncontradoException", "LivroNullException"})
    public void atualizarLivro(LivroRequest resquest, Integer id){

        LivroResponse livroResponseSalvo = buscarPorId(id);
        EditoraResponse editoraResponse = editoraService.buscarPorId(resquest.getEditoraId());
        Editora editora = editoraService.editoraResponseToEditora(editoraResponse);
        Livro livroSalvo = livroResponseToLivro(livroResponseSalvo, editora);
        BeanUtils.copyProperties(resquest, livroSalvo, "dataCriacao");
        livroSalvo.setId(id);
        livroSalvo.setDataCriacao(livroResponseSalvo.getDataCriacao());
        livroSalvo.setDataAtualizacao(LocalDate.now());
        repository.save(livroSalvo);
    }

    @Transactional
    public void apagarLivro(Integer id){
        LivroResponse editoraResponse = buscarPorId(id);
        List<Livro> livros = repository.findByEditoraId(id);

        //add verificação se o livroId esta na tabela livros_autores

        repository.deleteById(id);
    }

    public Page<LivroResponse> buscarTodasEditoras(Integer page, Integer linesPerPage,
                                                     String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest).map(LivroResponse::new);
    }

    public Optional<Livro> buscarLivroPorEditora(String titulo, Integer editoraId){
        return repository.findByTituloAndEditoraId(titulo, editoraId);
    }

    public List<Livro> buscarLivrosPorEditoraId(Integer id){
        return repository.findByEditoraId(id);
    }

    public Livro livroRequestToLivro(LivroRequest dto, Editora editora){
        return new Livro(
                null,
                dto.getTitulo(),
                dto.getSubtitulo(),
                dto.getTitulo(),
                dto.getPaginas(),
                dto.getIsbn(),
                editora
        );
    }

    public Livro livroResponseToLivro(LivroResponse dto, Editora editora){
        return new Livro(
                dto.getId(),
                dto.getTitulo(),
                dto.getSubtitulo(),
                dto.getTitulo(),
                dto.getPaginas(),
                dto.getIsbn(),
                editora
        );
    }
}
