package com.biblioteca.config;

import com.biblioteca.resource.dto.request.EditoraRequest;
import com.biblioteca.service.EditoraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PopularDB {

    private EditoraService editoraService;

    private Logger LOG = LoggerFactory.getLogger(PopularDB.class);

    @Autowired
    public PopularDB(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    public void salvarEditora(){
        LOG.info("Salvando editora no banco de dados.");
        EditoraRequest editoraRequest = new EditoraRequest(
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
                "RJ");
        try{
            editoraService.salvar(editoraRequest);
            LOG.info("Editora salva com sucesso!");
        } catch (Exception ex){
            LOG.error("Editora não foi salva no banco de dados.");
            LOG.error("Exception class = {}, mensagem = {}", ex.getClass().getName(), ex.getMessage());
        }
    }
}
