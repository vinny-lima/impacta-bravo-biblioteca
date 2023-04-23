package com.biblioteca.service;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.repository.EditoriaRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.resource.dto.request.EditoraRequest;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.service.exceptions.EditoraIntegridadeDadosException;
import com.biblioteca.service.exceptions.EditoraJaCadastradaException;
import com.biblioteca.service.exceptions.EditoraNaoEncontradaException;
import com.biblioteca.service.exceptions.EditoraNullException;
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

@Service
public class EditoraService {

    private final EditoriaRepository repository;
    private final LivroRepository livroRepository;
    private final Logger LOG = LoggerFactory.getLogger(EditoraService.class);

    @Autowired
    public EditoraService(EditoriaRepository repository, LivroRepository livroRepository) {
        this.repository = repository;
        this.livroRepository = livroRepository;
    }

    @Transactional(rollbackFor = {
            EditoraJaCadastradaException.class,
            EditoraNullException.class
    })
    public EditoraResponse salvar(EditoraRequest dto){
        LOG.info("EditoraService - salvar");

        if (dto == null) throw new EditoraNullException("EditoraResquest nulo.");

        if (editoraExiste(dto))
            throw new EditoraJaCadastradaException("Editora já existe cadastrada com essa razão social: "+dto.getRazaoSocial());

        Editora editora = editoraRequestToEditora(dto);
        editora.setDataCriacao(LocalDate.now());
        editora.setDataAtualizacao(LocalDate.now());

        LOG.info("Salvando editora: {}", dto);
        Editora editoraSalva = repository.save(editora);

        return new EditoraResponse(editoraSalva);
    }

    public EditoraResponse buscarPorId(Integer id){
        LOG.info("EditoraService - buscarPorId");

        if (id == null) throw new EditoraNullException("Id nulo para buscar editora por id.");

        LOG.info("Buscando editora por id: {}", id);
        Optional<Editora> optionalEditora = repository.findById(id);

        if (optionalEditora.isEmpty()) throw new EditoraNaoEncontradaException("Editora não encontrada com id: "+id);

        return new EditoraResponse(optionalEditora.get());
    }

    public Page<EditoraResponse> buscarTodasEditorasPaginada(Integer page, Integer linesPerPage,
                                                             String direction, String orderBy) {
        LOG.info("EditoraService - buscarTodasEditorasPaginada");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        LOG.info("Buscando editoras paginadas");
        return repository.findAll(pageRequest).map(EditoraResponse::new);
    }

    @Transactional(rollbackFor = {
            EditoraNaoEncontradaException.class,
            EditoraNullException.class
    })
    public EditoraResponse atualizarEditora(EditoraRequest resquest, Integer id){
        LOG.info("EditoraService - atualizarEditora");

        EditoraResponse editoraResponseSalva = buscarPorId(id);

        LOG.info("Atualizando editora com id: {}", id);
        Editora editoraSalva = editoraResponseToEditora(editoraResponseSalva);
        BeanUtils.copyProperties(resquest, editoraSalva, "dataCriacao");

        editoraSalva.setId(id);
        editoraSalva.setDataCriacao(editoraResponseSalva.getDataCriacao());
        editoraSalva.setDataAtualizacao(LocalDate.now());

        Editora editora = repository.save(editoraSalva);
        return new EditoraResponse(editora);
    }

    @Transactional(rollbackFor = {
            EditoraNaoEncontradaException.class,
            EditoraNullException.class,
            EditoraIntegridadeDadosException.class
    })
    public void apagarEditora(Integer id){
        LOG.info("EditoraService - apagarEditora");

        EditoraResponse editoraResponse = buscarPorId(id);

        LOG.info("Buscando livro por editora id: {}", id);
        List<Livro> livros = livroRepository.findByEditoraId(id);

        if (!livros.isEmpty()) throw new EditoraIntegridadeDadosException("Não foi possivel apagar editora: "
        +editoraResponse.getRazaoSocial()+ ", Pois ela tem livros atrelados a ela salvos no banco de dados.");

        LOG.info("Apagando editora com id: {}", id);
        repository.deleteById(id);
    }

    private boolean editoraExiste(EditoraRequest editoraResquest){
        return repository.findByRazaoSocial(editoraResquest.getRazaoSocial()).isPresent();

    }

    public Editora editoraResponseToEditora(EditoraResponse editoraResponse){
        return new Editora(
                editoraResponse.getId(),
                editoraResponse.getRazaoSocial(),
                editoraResponse.getNomeFantasia(),
                editoraResponse.getDocumento(),
                editoraResponse.getTelefone(),
                editoraResponse.getEmail(),
                editoraResponse.getLogradouro(),
                editoraResponse.getNumero(),
                editoraResponse.getComplemento(),
                editoraResponse.getBairro(),
                editoraResponse.getMunicipio(),
                editoraResponse.getUf(),
                editoraResponse.getDataCriacao(),
                editoraResponse.getDataAtualizacao(),
                editoraResponse.getCep()
        );
    }

    private Editora editoraRequestToEditora(EditoraRequest editoraResquest){
        return new Editora(
                null,
                editoraResquest.getRazaoSocial(),
                editoraResquest.getNomeFantasia(),
                editoraResquest.getDocumento(),
                editoraResquest.getTelefone(),
                editoraResquest.getEmail(),
                editoraResquest.getLogradouro(),
                editoraResquest.getNumero(),
                editoraResquest.getComplemento(),
                editoraResquest.getBairro(),
                editoraResquest.getMunicipio(),
                editoraResquest.getUf(),
                null,
                null,
                editoraResquest.getCep()
        );
    }
}
