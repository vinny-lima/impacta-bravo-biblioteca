package com.biblioteca.service;

import com.biblioteca.entities.Livro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private LivroRepository repository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.repository = livroRepository;
    }

    public List<Livro> buscarLivrosPorEditoraId(Integer id){
        return repository.findByEditoraId(id);
    }
}
