package com.biblioteca.resource;

import com.biblioteca.resource.dto.request.EditoraResquest;
import com.biblioteca.resource.dto.response.EditoraResponse;
import com.biblioteca.service.EditoraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/editoras")
public class EditoraResource {

    private EditoraService service;

    @Autowired
    public EditoraResource(EditoraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid @RequestBody EditoraResquest request){
        EditoraResponse editoraResponse = service.salvar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(editoraResponse.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponse> buscarEditoraPorId(@PathVariable Integer id){
         return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EditoraResponse>> buscarTodasEditoras(){
        return ResponseEntity.ok(service.buscarTodasEditoras());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarEditora(@Valid @RequestBody EditoraResquest request,
                                                 @PathVariable Integer id){
        service.atualizarEditora(request, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarEditora(@PathVariable Integer id){
        service.apagarEditora(id);
        return ResponseEntity.noContent().build();
    }
}
