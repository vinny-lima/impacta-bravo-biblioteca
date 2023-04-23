package com.biblioteca.resource;

import com.biblioteca.resource.dto.request.LivroRequest;
import com.biblioteca.resource.dto.response.LivroResponse;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/livros")
public class LivroResource {

    private final LivroService service;

    @Autowired
    public LivroResource(LivroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> salvar(@Valid @RequestBody LivroRequest request){
        LivroResponse livroResponse = service.salvar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(livroResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(livroResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarLivroPorId(@PathVariable Integer id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<LivroResponse>> buscarTodosLivros(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy", defaultValue = "isbn")String orderBy){
        return ResponseEntity.ok(service.buscarTodosLivrosPaginado(page, linesPerPage, direction, orderBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizarLivro(
            @Valid @RequestBody LivroRequest request,
            @PathVariable Integer id){
        LivroResponse livroResponse = service.atualizarLivro(request, id);
        return ResponseEntity.ok(livroResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarLivro(@PathVariable Integer id){
        service.apagarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
