package com.biblioteca.service;

import com.biblioteca.entities.Editora;
import com.biblioteca.entities.Livro;
import com.biblioteca.repository.EditoriaRepository;
import com.biblioteca.resource.dto.request.EditoraResquest;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.service.exceptions.EditoraIntegridadeDadosException;
import com.biblioteca.service.exceptions.EditoraJaCadastradaException;
import com.biblioteca.service.exceptions.EditoraNaoEncontradaException;
import com.biblioteca.service.exceptions.EditoraNullException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EditoraService {

    private EditoriaRepository repository;
    private LivroService livroService;

    @Autowired
    public EditoraService(EditoriaRepository repository, LivroService livroService) {
        this.repository = repository;
        this.livroService = livroService;
    }

    @Transactional(rollbackForClassName = {"EditoraJaCadastradaException", "EditoraNullException"})
    public EditoraResponse salvar(EditoraResquest dto){
        if (repository == null) throw new EditoraNullException("EditoraResquest nulo.");

        if (editoraExiste(dto)) throw new EditoraJaCadastradaException("Editora já existe cadastrada com essa razão social: "
                + dto.getRazaoSocial());

        Editora editora = editoraRequestToEditora(dto);
        editora.setDataCriacao(LocalDate.now());
        editora.setDataAtualizacao(LocalDate.now());

        Editora editoraSalva = repository.save(editora);

        return new EditoraResponse(editoraSalva);
    }

    public EditoraResponse buscarPorId(Integer id){

        if (id == null) throw new EditoraNullException("Id nulo para buscar editora por id.");

        Optional<Editora> optionalEditora = repository.findById(id);

        if (optionalEditora.isEmpty()) throw new EditoraNaoEncontradaException("Editora não encontrada com id: "+id);

        return new EditoraResponse(optionalEditora.get());
    }

    public List<EditoraResponse> buscarTodasEditoras(){
        return repository.findAll().stream().map(editora -> new EditoraResponse(editora)).toList();
    }

    @Transactional(rollbackForClassName = {"EditoraNaoEncontradaException", "EditoraNullException"})
    public void atualizarEditora(EditoraResquest resquest, Integer id){

        EditoraResponse editoraResponseSalva = buscarPorId(id);
        Editora editoraSalva = editoraResponseToEditora(editoraResponseSalva);
        BeanUtils.copyProperties(resquest, editoraSalva);
        editoraSalva.setId(id);
        editoraSalva.setDataAtualizacao(LocalDate.now());
        repository.save(editoraSalva);
    }

    @Transactional(rollbackForClassName = {"EditoraNaoEncontradaException", "EditoraNullException",
            "EditoraIntegridadeDadosException"})
    public void apagarEditora(Integer id){
        EditoraResponse editoraResponse = buscarPorId(id);
        List<Livro> livros = livroService.buscarLivrosPorEditoraId(id);

        if (!livros.isEmpty()) throw new EditoraIntegridadeDadosException("Não foi possivel apagar editora: "
        +editoraResponse.getRazaoSocial()+ ", Pois ela tem livros atrelados a ela salvos no banco de dados.");

        repository.deleteById(id);
    }

    private boolean editoraExiste(EditoraResquest editoraResquest){
        Optional<Editora> optionalEditora = repository.findByRazaoSocial(editoraResquest.getRazaoSocial());
        return optionalEditora.isPresent();
    }

    private Editora editoraResponseToEditora(EditoraResponse editoraResponse){
        return new Editora(
                null,
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
                editoraResponse.getUf()
        );
    }

    private Editora editoraRequestToEditora(EditoraResquest editoraResquest){
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
                editoraResquest.getUf()
        );
    }
}
