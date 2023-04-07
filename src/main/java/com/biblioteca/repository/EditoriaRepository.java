package com.biblioteca.repository;

import com.biblioteca.entities.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditoriaRepository extends JpaRepository<Editora, Integer> {

    Optional<Editora> findByRazaoSocial(String razaoSocial);
}
