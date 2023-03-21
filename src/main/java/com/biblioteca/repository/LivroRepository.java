package com.biblioteca.repository;

import com.biblioteca.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    List<Livro> findByEditoraId(Integer id);

    Optional<Livro> findByTituloAndEditoraId(String titulo,Integer editoraId);
}
