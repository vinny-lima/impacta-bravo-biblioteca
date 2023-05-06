package com.biblioteca.service;

import com.biblioteca.entities.GeneroLiterario;
import com.biblioteca.repository.GeneroLiterarioRepository;
import com.biblioteca.resource.dto.request.GeneroLiterarioRequest;
import com.biblioteca.resource.dto.response.GeneroLiterarioResponse;
import com.biblioteca.service.exceptions.GeneroLiterarioIntegridadeDadosException;
import com.biblioteca.service.exceptions.GeneroLiterarioJaCadastradoException;
import com.biblioteca.service.exceptions.GeneroLiterarioNaoEncontradoException;
import com.biblioteca.service.exceptions.GeneroLiterarioNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroLiterarioService {

    private final GeneroLiterarioRepository repository;
    private final Logger LOG = LoggerFactory.getLogger(GeneroLiterarioService.class);

    @Autowired
    public GeneroLiterarioService(GeneroLiterarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackFor = {GeneroLiterarioNullException.class, GeneroLiterarioJaCadastradoException.class})
    public GeneroLiterarioResponse salvar(GeneroLiterarioRequest dto) {

        LOG.info("GeneroLiterarioService - salvar");

        if (dto == null) throw new GeneroLiterarioNullException("GeneroLiterarioRequest dto é nulo.");

        Optional<GeneroLiterario> optionalGeneroLiterario = repository.findByDescricao(dto.getDescricao());

        if (optionalGeneroLiterario.isPresent())
            throw new GeneroLiterarioJaCadastradoException(
                    "Esse genero liteario já está cadastrado com esse nome: " + dto.getDescricao());

        GeneroLiterario generoLiterario = generoLitearioRequestToGeneroLiteario(dto);

        LOG.info("Salvando genero liteario: {}", dto);
        GeneroLiterario generoLiterarioSalvo = repository.save(generoLiterario);
        return new GeneroLiterarioResponse(generoLiterarioSalvo);
    }

    @Transactional(rollbackFor = {GeneroLiterarioNaoEncontradoException.class, GeneroLiterarioNullException.class})
    public GeneroLiterarioResponse atualizar(GeneroLiterarioRequest dto, Integer id) {

        LOG.info("GeneroLiterarioService - atualizar");

        GeneroLiterarioResponse generoLiterarioReponseSalvo = buscarPorId(id);

        LOG.info("Atualizando genero liteario com id: {}", id);
        GeneroLiterario generoLiterarioSalvo = generoLiterarioResponseToGeneroLiterario(generoLiterarioReponseSalvo);
        BeanUtils.copyProperties(dto, generoLiterarioSalvo);
        generoLiterarioSalvo.setId(id);

        GeneroLiterario generoLiterario = repository.save(generoLiterarioSalvo);
        return new GeneroLiterarioResponse(generoLiterario);
    }

    @Transactional(
            rollbackFor = {
                    GeneroLiterarioNullException.class,
                    GeneroLiterarioNaoEncontradoException.class,
                    GeneroLiterarioIntegridadeDadosException.class
            })
    public void apagar(Integer id) {

        LOG.info("GeneroLiterarioService - apagar");

        GeneroLiterarioResponse generoLiterarioReponseSalvo = buscarPorId(id);

        GeneroLiterario GeneroLiterario = generoLiterarioResponseToGeneroLiterario(generoLiterarioReponseSalvo);

        if (!GeneroLiterario.getLivros().isEmpty())
            throw new GeneroLiterarioIntegridadeDadosException(
                    String.format("Genero literario %s possui livros cadastrado no banco de dados", GeneroLiterario.getDescricao()));

        LOG.info("Apagando genero literario com id: {}", id);
        repository.delete(GeneroLiterario);
    }

    public Page<GeneroLiterarioResponse> buscarTodosGenerosLiterariosPaginado(Integer page, Integer linesPerPage,
                                                                              String direction, String orderBy) {
        LOG.info("GeneroLiterarioService - buscarTodosGenerosLiterariosPaginado");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        LOG.info("Buscando todos genero literario paginado");
        return repository.findAll(pageRequest).map(GeneroLiterarioResponse::new);
    }

    public List<GeneroLiterarioResponse> buscarGenerosLiterariosPorListaIds(List<Integer> generosLiterariosId) {
        LOG.info("GeneroLiterarioService - buscarGenerosLiterariosPorListaIds");
        try {
            LOG.info("Buscando genero literario por lista de ids: {}", generosLiterariosId);
            List generosLiterariosLista = new ArrayList<>();
            for (Integer autorId : generosLiterariosId) {
                GeneroLiterarioResponse generoLiterarioResponse = buscarPorId(autorId);
                generosLiterariosLista.add(generoLiterarioResponse);
            }
            LOG.info("Generos literarios buscados por lista de ids com sucesso!");
            return generosLiterariosLista;
        } catch (Exception ex) {
            LOG.error("Erro ao buscar generos literarios por lista de ids: {}", generosLiterariosId.toString());
            LOG.error("Exception: {}, mensagem: {}, cause: {}", ex.getClass().getName(), ex.getMessage(), ex.getCause());
            throw ex;
        }

    }

    public GeneroLiterarioResponse buscarPorId(Integer id) {

        LOG.info("GeneroLiterarioService - buscarPorId");

        if (id == null) throw new GeneroLiterarioNullException("Id nulo para buscar genero literario por id.");

        LOG.info("Buscando genero literario por id: {}", id);
        Optional<GeneroLiterario> optionalGeneroLiterario = repository.findById(id);

        if (optionalGeneroLiterario.isEmpty())
            throw new GeneroLiterarioNaoEncontradoException("Genero literario não encontrada com id: " + id);

        return new GeneroLiterarioResponse(optionalGeneroLiterario.get());
    }

    public GeneroLiterario generoLitearioRequestToGeneroLiteario(GeneroLiterarioRequest dto) {
        return new GeneroLiterario(null, dto.getDescricao());
    }

    public GeneroLiterario generoLiterarioResponseToGeneroLiterario(GeneroLiterarioResponse dto) {
        return new GeneroLiterario(dto.getId(), dto.getDescricao());
    }

    public List<GeneroLiterario> listGeneroLiterarioResponseToGeneroLiterario(List<GeneroLiterarioResponse> generoLiterarioResponseList) {
        return generoLiterarioResponseList.stream().map(this::generoLiterarioResponseToGeneroLiterario).toList();
    }
}
