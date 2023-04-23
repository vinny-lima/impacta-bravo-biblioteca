package com.biblioteca.resource;

import com.biblioteca.resource.dto.request.EditoraRequest;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.service.EditoraService;
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
@RequestMapping("/editoras")
public class EditoraResource {

    private final EditoraService service;

    @Autowired
    public EditoraResource(EditoraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EditoraResponse> salvar(@Valid @RequestBody EditoraRequest request){
        EditoraResponse editoraResponse = service.salvar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(editoraResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(editoraResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponse> buscarEditoraPorId(@PathVariable Integer id){
         return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<EditoraResponse>> buscarTodasEditoras(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy", defaultValue = "nomeFantasia")String orderBy){
        return ResponseEntity.ok(service.buscarTodasEditorasPaginada(page, linesPerPage, direction, orderBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponse> atualizarEditora(
            @Valid @RequestBody EditoraRequest request,
            @PathVariable Integer id){
        EditoraResponse editoraResponse = service.atualizarEditora(request, id);
        return ResponseEntity.ok(editoraResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarEditora(@PathVariable Integer id){
        service.apagarEditora(id);
        return ResponseEntity.noContent().build();
    }
}
