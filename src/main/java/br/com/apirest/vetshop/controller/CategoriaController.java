package br.com.apirest.vetshop.controller;

import br.com.apirest.vetshop.model.Categoria;
import br.com.apirest.vetshop.service.CategoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) throws BadRequestException {
        categoria = validateCategoria(categoria);

        if (Objects.isNull(categoria)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Categoria newCategoria = categoriaService.Create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> existingCategoria = categoriaService.findById(id);
        if (!existingCategoria.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        categoria.setId(id);
        Categoria validCategoria = validateCategoria(categoria);

        if (Objects.isNull(validCategoria)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        Categoria updatedCategoria = categoriaService.Edit(validCategoria);
        return ResponseEntity.ok(updatedCategoria);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        Categoria categoriaToDelete = new Categoria();
        categoriaToDelete.setId(id);
        Categoria deletedCategoria = categoriaService.Delete(categoriaToDelete);

        if (deletedCategoria != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Categoria validateCategoria(Categoria categoria) {
        if (Objects.isNull(categoria) || Objects.isNull(categoria.getNome())) {
            return null;
        }
        return categoria;
    }
}
