package com.biblioteca.resource;

import com.biblioteca.resource.dto.request.AutorRequest;
import com.biblioteca.resource.dto.response.AutorResponse;
import com.biblioteca.service.AutorService;
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
@RequestMapping("/autores")
public class AutorResource {

    private final AutorService service;

    @Autowired
    public AutorResource(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AutorResponse> salvar(@Valid @RequestBody AutorRequest request){
        AutorResponse autorResponse = service.salvar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(autorResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(autorResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> buscarAutorPorId(@PathVariable Integer id){
         return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<AutorResponse>> buscarTodosAutores(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy", defaultValue = "nomeFantasia")String orderBy){
        return ResponseEntity.ok(service.buscarTodosAutoresPaginado(page, linesPerPage, direction, orderBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizarAutor(
            @Valid @RequestBody AutorRequest request,
            @PathVariable Integer id){
        AutorResponse autorResponse = service.atualizar(request, id);
        return ResponseEntity.ok(autorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAutor(@PathVariable Integer id){
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
