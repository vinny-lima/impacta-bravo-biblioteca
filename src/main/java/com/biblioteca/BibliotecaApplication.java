package com.biblioteca;

import com.biblioteca.config.PopularDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {

    @Autowired
    private PopularDB popularDB;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        popularDB.salvarEditora();
        popularDB.salvarAutores();
    }
}
