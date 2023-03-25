package com.biblioteca;

import com.biblioteca.config.PopularDB;
import com.biblioteca.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {

    @Autowired
    private EditoraService editoraService;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        new PopularDB(editoraService).salvarEditora();
    }
}
