package com.biblioteca.config;

import com.biblioteca.resource.dto.request.AutorRequest;
import com.biblioteca.resource.dto.request.EditoraRequest;
import com.biblioteca.resource.dto.request.GeneroLiterarioRequest;
import com.biblioteca.service.AutorService;
import com.biblioteca.service.EditoraService;
import com.biblioteca.service.GeneroLiterarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

public class PopularDB {

    private final EditoraService editoraService;
    private final AutorService autorService;
    private final GeneroLiterarioService generoLiterarioService;
    private final Logger LOG = LoggerFactory.getLogger(PopularDB.class);

    @Autowired
    public PopularDB(EditoraService editoraService, AutorService autorService,
                     GeneroLiterarioService generoLiterarioService) {
        this.editoraService = editoraService;
        this.autorService = autorService;
        this.generoLiterarioService = generoLiterarioService;
    }

    public void salvarEditora(){
        LOG.info("Salvando editora no banco de dados.");
        var editoraRequest = new EditoraRequest(
                "EDITORA VERA CRUZ LTDA",
                "LEYA",
                "08.108.543/0006-43",
                "(11)3855-2109",
                "contabilidade@escala.com.br",
                "Avenida Calogeras",
                6,
                "Apt 701",
                "Centro",
                "Rio de Janeiro",
                "RJ",
                "20030-000");
        try{
            editoraService.salvar(editoraRequest);
            LOG.info("Editora salva com sucesso!");
        } catch (Exception ex){
            LOG.error("Editora não foi salva no banco de dados.");
            LOG.error("Exception class = {}, mensagem = {}", ex.getClass().getName(), ex.getMessage());
        }
    }

    public void salvarAutores(){
        LOG.info("Salvando autores no banco de dados.");
        var autor1 = new AutorRequest("Anthony E. Zuiker", "Anthony E. Zuiker");
        var autor2 = new AutorRequest("Duane Swierczynsk", "Duane Swierczynsk");
        try{
            autorService.salvar(autor1);
            autorService.salvar(autor2);
            LOG.info("Autores salvos com sucesso!");
        } catch (Exception ex){
            LOG.error("Autores não foi salva no banco de dados.");
            LOG.error("Exception class = {}, mensagem = {}", ex.getClass().getName(), ex.getMessage());
        }
    }

    public void salvarGenerosLiterarios(){
        LOG.info("Salvando generos literarios no banco de dados.");
        var listaGenerosLiterarios = Arrays.asList(
                new GeneroLiterarioRequest("Terror"),
                new GeneroLiterarioRequest("Drama"),
                new GeneroLiterarioRequest("Suspense"),
                new GeneroLiterarioRequest("Ação")
        );
        try{
            listaGenerosLiterarios.stream().forEach(generoLiterarioService::salvar);
            LOG.info("Generos literarios salvos com sucesso!");
        } catch (Exception ex){
            LOG.error("Generos literarios não foi salva no banco de dados.");
            LOG.error("Exception class = {}, mensagem = {}", ex.getClass().getName(), ex.getMessage());
        }
    }
}

@Configuration
class PopularConfigBean{

    @Autowired
    private EditoraService editoraService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private GeneroLiterarioService generoLiterarioService;

    @Bean
    public PopularDB popularDB(EditoraService editoraService, AutorService autorService,
                               GeneroLiterarioService generoLiterarioService){
        return new PopularDB(editoraService, autorService, generoLiterarioService);
    }
}
