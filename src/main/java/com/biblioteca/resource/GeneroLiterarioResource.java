package com.biblioteca.resource;

import com.biblioteca.resource.dto.request.GeneroLiterarioRequest;
import com.biblioteca.resource.dto.response.GeneroLiterarioResponse;
import com.biblioteca.service.GeneroLiterarioService;
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
@RequestMapping("/generos-literarios")
public class GeneroLiterarioResource {

    private final GeneroLiterarioService service;

    @Autowired
    public GeneroLiterarioResource(GeneroLiterarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GeneroLiterarioResponse> salvar(@Valid @RequestBody GeneroLiterarioRequest request){
        GeneroLiterarioResponse generoLiterarioResponse = service.salvar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(generoLiterarioResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(generoLiterarioResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroLiterarioResponse> buscarPorId(@PathVariable Integer id){
         return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<GeneroLiterarioResponse>> buscaPaginada(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy", defaultValue = "descricao")String orderBy){
        return ResponseEntity.ok(service.buscarTodosGenerosLiterariosPaginado(page, linesPerPage, direction, orderBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroLiterarioResponse> atualizar(
            @Valid @RequestBody GeneroLiterarioRequest request,
            @PathVariable Integer id){
        return ResponseEntity.ok(service.atualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Integer id){
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
