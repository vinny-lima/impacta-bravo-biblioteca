package com.biblioteca.service;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.resource.dto.request.LivroRequest;
import com.biblioteca.resource.dto.response.AutorResponse;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.resource.dto.response.GeneroLiterarioResponse;
import com.biblioteca.resource.dto.response.LivroResponse;
import com.biblioteca.service.exceptions.AutorNaoEncontradoException;
import com.biblioteca.service.exceptions.AutorNullException;
import com.biblioteca.service.exceptions.EditoraNaoEncontradaException;
import com.biblioteca.service.exceptions.EditoraNullException;
import com.biblioteca.service.exceptions.GeneroLiterarioNaoEncontradoException;
import com.biblioteca.service.exceptions.GeneroLiterarioNullException;
import com.biblioteca.service.exceptions.LivroJaCadastradoException;
import com.biblioteca.service.exceptions.LivroNaoEncontradoException;
import com.biblioteca.service.exceptions.LivroNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.biblioteca.entities.Autor.toListaAutor;
import static com.biblioteca.entities.GeneroLiterario.toListaGeneroLiterario;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final EditoraService editoraService;
    private final AutorService autorService;
    private final GeneroLiterarioService generoLiterarioService;
    private final Logger LOG = LoggerFactory.getLogger(LivroService.class);

    @Autowired
    public LivroService(LivroRepository repository, EditoraService editoraService,
                        AutorService autorService, GeneroLiterarioService generoLiterarioService) {
        this.repository = repository;
        this.editoraService = editoraService;
        this.autorService = autorService;
        this.generoLiterarioService = generoLiterarioService;
    }

    @Transactional(
            rollbackFor = {
                    LivroNullException.class,
                    LivroJaCadastradoException.class,
                    AutorNullException.class,
                    AutorNaoEncontradoException.class,
                    EditoraNullException.class,
                    EditoraNaoEncontradaException.class,
                    GeneroLiterarioNullException.class,
                    GeneroLiterarioNaoEncontradoException.class
            })
    public LivroResponse salvar(LivroRequest dto){

        LOG.info("LivroService - salvar");
        if (dto == null) throw new LivroNullException("LivroRequest dto é nulo.");

        Optional<Livro> optionalLivro = buscarLivroPorEditora(dto.getIsbn(), dto.getEditoraId());

        if (optionalLivro.isPresent())
            throw new LivroJaCadastradoException("Esse livro já está cadastrado com esse isbn: "
            +dto.getIsbn()+ ", Para essa editora: " +optionalLivro.get().getEditora().getNomeFantasia());

        try {
            LOG.info("Buscando editora por id e adicionando editora ao livro");
            EditoraResponse editoraResponse = editoraService.buscarPorId(dto.getEditoraId());
            Editora editora = editoraService.editoraResponseToEditora(editoraResponse);

            Livro livro = livroRequestToLivro(dto, editora);
            livro.setDataCriacao(LocalDate.now());
            livro.setDataAtualizacao(LocalDate.now());

            LOG.info("Buscando autores por ids e adicionando autores ao livro");
            List<AutorResponse> autorResponse = autorService.buscarAutoresPorListaIds(dto.getAutoresId());
            livro.adicionarAutores(autorService.listAutorResponseToAutor(autorResponse));

            LOG.info("Buscando generos literarios por ids e adicionando generos literarios ao livro");
            List<GeneroLiterarioResponse> generoLiterarioResponse = generoLiterarioService
                    .buscarGenerosLiterariosPorListaIds(dto.getGenerosLiterariosId());
            livro.adicionarGenerosLiterarios(generoLiterarioService
                    .listGeneroLiterarioResponseToGeneroLiterario(generoLiterarioResponse));

            return new LivroResponse(repository.save(livro));
        } catch (Exception ex){
            LOG.error("LivroService - salvar");
            LOG.error("Erro ao salvar livro: {}", dto);
            LOG.error("Exception: {}, mensagem: {}, cause: {}", ex.getClass().getName(), ex.getMessage(), ex.getCause());
            throw ex;
        }
    }

    public LivroResponse buscarPorId(Integer id){
        LOG.info("LivroService - buscarPorId");
        if (id == null) throw new LivroNullException("Id nulo para buscar livro por id.");

        Optional<Livro> optionalLivro = repository.findById(id);

        if (optionalLivro.isEmpty()) throw new LivroNaoEncontradoException("Livro não encontrada com id: "+id);

        LOG.info("Buscando livro por id: {}", id);
        return new LivroResponse(optionalLivro.get());
    }

    @Transactional(rollbackFor = {LivroNaoEncontradoException.class, LivroNullException.class})
    public LivroResponse atualizarLivro(LivroRequest resquest, Integer id){
        LOG.info("LivroService - atualizarLivro");

        LivroResponse livroResponseSalvo = buscarPorId(id);

        LOG.info("Buscando editora por id e adicionando editora ao livro");
        EditoraResponse editoraResponse = editoraService.buscarPorId(resquest.getEditoraId());
        Editora editora = editoraService.editoraResponseToEditora(editoraResponse);

        LOG.info("Atualizando livro com id: {}", id);
        Livro livroSalvo = livroResponseToLivro(livroResponseSalvo, editora);

        //adiconando os autores no livro
        livroSalvo.adicionarAutores(toListaAutor(livroResponseSalvo.getAutores()));

        //adiconando os generos literarios no livro
        livroSalvo.adicionarGenerosLiterarios(toListaGeneroLiterario(livroResponseSalvo.getGenerosLiterarios()));

        BeanUtils.copyProperties(resquest, livroSalvo, "dataCriacao", "isbn");

        livroSalvo.setId(id);
        livroSalvo.setDataCriacao(livroResponseSalvo.getDataCriacao());
        livroSalvo.setDataAtualizacao(LocalDate.now());

        Livro livro = repository.save(livroSalvo);

        livro.adicionarAutores(livroSalvo.getAutores());
        livro.adicionarGenerosLiterarios(livroSalvo.getGenerosLiterarios());

        return livroToLivroResponse(livro, editora);
    }

    @Transactional(rollbackFor = {
            LivroNaoEncontradoException.class,
            LivroNullException.class
    })
    public void apagarLivro(Integer id){
        LOG.info("LivroService - apagarLivro");

        LivroResponse livroResponseSalvo = buscarPorId(id);
        Livro livroSalvo = livroResponseToLivro(livroResponseSalvo, livroResponseSalvo.getEditora());

        LOG.info("Removendo autores para apagar livro");
        livroSalvo.removerAutores();

        LOG.info("Removendo generos literarios para apagar livro");
        livroSalvo.removerGenerosLiterarios();

        LOG.info("Apagando livro com id: {}", id);
        repository.delete(livroSalvo);
    }

    public Page<LivroResponse> buscarTodosLivrosPaginado(Integer page, Integer linesPerPage,
                                                         String direction, String orderBy) {
        LOG.info("LivroService - buscarTodosLivrosPaginado");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        LOG.info("Buscando todos livros paginando");
        return repository.findAll(pageRequest).map(LivroResponse::new);
    }

    public Optional<Livro> buscarLivroPorEditora(String isbn, Integer editoraId){
        return repository.findByIsbnAndEditoraId(isbn, editoraId);
    }

    public List<Livro> buscarLivrosPorEditoraId(Integer id){
        return repository.findByEditoraId(id);
    }

    public Livro livroRequestToLivro(LivroRequest dto, Editora editora){
        return new Livro(
                null,
                dto.getTitulo(),
                dto.getSubtitulo(),
                dto.getDescricao(),
                dto.getPaginas(),
                dto.getIsbn(),
                editora,
                dto.getQuantidade()
        );
    }

    public Livro livroResponseToLivro(LivroResponse dto, Editora editora){
        return new Livro(
                dto.getId(),
                dto.getTitulo(),
                dto.getSubtitulo(),
                dto.getDescricao(),
                dto.getPaginas(),
                dto.getIsbn(),
                editora,
                dto.getQuantidade()
        );
    }

    public LivroResponse livroToLivroResponse(Livro livro, Editora editora){
        livro.setEditora(editora);
        return new LivroResponse(livro);
    }
}
