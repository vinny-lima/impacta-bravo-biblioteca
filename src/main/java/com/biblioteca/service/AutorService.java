package com.biblioteca.service;

import com.biblioteca.entities.Autor;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.resource.dto.request.AutorRequest;
import com.biblioteca.resource.dto.response.AutorResponse;
import com.biblioteca.service.exceptions.AutorIntegridadeDadosException;
import com.biblioteca.service.exceptions.AutorJaCadastradaException;
import com.biblioteca.service.exceptions.AutorNaoEncontradoException;
import com.biblioteca.service.exceptions.AutorNullException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final Logger LOG = LoggerFactory.getLogger(AutorService.class);
    @Autowired
    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackFor = {AutorNullException.class, AutorJaCadastradaException.class})
    public AutorResponse salvar(AutorRequest dto){
        LOG.info("AutorService - salvar");

        if (dto == null) throw new AutorNullException("AutorRequest dto é nulo.");

        Optional<Autor> optionalAutor = repository.findByNome(dto.getNome());

        if (optionalAutor.isPresent())
            throw new AutorJaCadastradaException("Esse autor já está cadastrado com esse nome: " +dto.getNome());

        Autor autor = autorRequestToAutor(dto);
        autor.setDataCriacao(LocalDate.now());
        autor.setDataAtualizacao(LocalDate.now());

        LOG.info("Salvando autor: {}", dto);
        Autor autorSalvo = repository.save(autor);
        return new AutorResponse(autorSalvo);
    }

    @Transactional(rollbackFor = {AutorNaoEncontradoException.class, AutorNullException.class})
    public AutorResponse atualizar(AutorRequest dto, Integer id){
        LOG.info("AutorService - atualizar");

        AutorResponse autorReponseSalvo = buscarPorId(id);

        LOG.info("Atualizando autor com id: {}", id);
        Autor autorSalvo = autorResponseToAutor(autorReponseSalvo);
        BeanUtils.copyProperties(dto, autorSalvo, "dataCriacao", "nome");
        autorSalvo.setId(id);

        autorSalvo.setDataCriacao(autorReponseSalvo.getDataCriacao());
        autorSalvo.setDataAtualizacao(LocalDate.now());

        Autor autor = repository.save(autorSalvo);
        return new AutorResponse(autor);
    }

    @Transactional(
            rollbackFor = {
            AutorNullException.class,
            AutorNaoEncontradoException.class,
            AutorIntegridadeDadosException.class
    })
    public void apagar(Integer id){
        LOG.info("AutorService - apagar");
        AutorResponse autorReponseSalvo = buscarPorId(id);

        Autor autor = autorResponseToAutor(autorReponseSalvo);

        if (!autor.getLivros().isEmpty())
            throw new AutorIntegridadeDadosException(
                    String.format("Autor %s possui livros cadastrado no banco de dados", autor.getNome()));

        LOG.info("Apagando autor com id: {}", id);
        repository.delete(autor);
    }

    public Page<AutorResponse> buscarTodosAutoresPaginado(Integer page, Integer linesPerPage,
                                                          String direction, String orderBy) {
        LOG.error("AutorService - buscarTodosAutoresPaginado");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        LOG.error("Buscando todos autores paginado");
        return repository.findAll(pageRequest).map(AutorResponse::new);
    }

    public List<AutorResponse> buscarAutoresPorListaIds(List<Integer> autoresId){
        LOG.error("AutorService - buscarAutoresPorListaIds");
        try {
            LOG.error("Buscando autores por lista de ids: {}", autoresId);
            List autoresLista = new ArrayList<>();
            for (Integer autorId : autoresId){
                AutorResponse autorResponse = buscarPorId(autorId);
                autoresLista.add(autorResponse);
            }
            LOG.error("Autores buscados por lista de ids com sucesso!");
            return autoresLista;
        }catch (Exception ex){
            LOG.error("Erro ao buscar autores por lista de ids: {}", autoresId.toString());
            LOG.error("Exception: {}, mensagem: {}, cause: {}", ex.getClass().getName(), ex.getMessage(), ex.getCause());
            throw ex;
        }

    }

    public AutorResponse buscarPorId(Integer id){
        LOG.error("AutorService - buscarPorId");

        if (id == null) throw new AutorNullException("Id nulo para buscar autor por id.");

        LOG.info("Buscando autor por id: {}", id);
        Optional<Autor> optionalAutor = repository.findById(id);

        if (optionalAutor.isEmpty()) throw new AutorNaoEncontradoException("Autor não encontrada com id: "+id);

        return new AutorResponse(optionalAutor.get());
    }

    public Autor autorRequestToAutor(AutorRequest dto){
        return new Autor(null, dto.getNome(), dto.getNomeFantasia(), null, null);
    }

    public Autor autorResponseToAutor(AutorResponse dto){
        return new Autor(dto.getId(), dto.getNome(), dto.getNomeFantasia(), dto.getDataCriacao(), dto.getDataAtualizacao());
    }

    public List<Autor> listAutorResponseToAutor(List<AutorResponse> autorResponseList){
        return autorResponseList.stream().map(this::autorResponseToAutor).toList();
    }
}
