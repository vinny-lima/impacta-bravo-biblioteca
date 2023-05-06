package com.biblioteca.repository;

import com.biblioteca.entities.GeneroLiterario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneroLiterarioRepository extends JpaRepository<GeneroLiterario, Integer> {

    Optional<GeneroLiterario> findByDescricao(String descricao);
}
