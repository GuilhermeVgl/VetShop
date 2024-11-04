package br.com.apirest.vetshop.controller;

import br.com.apirest.vetshop.model.Fornecedor;
import br.com.apirest.vetshop.service.FornecedorService;
import jakarta.transaction.Transactional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        return fornecedor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody Fornecedor fornecedor) throws BadRequestException {
        fornecedor = validateFornecedor(fornecedor);

        if (Objects.isNull(fornecedor)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Fornecedor newFornecedor = fornecedorService.Create(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
        Optional<Fornecedor> existingFornecedor = fornecedorService.findById(id);
        if (!existingFornecedor.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        fornecedor.setId(id);
        Fornecedor validFornecedor = validateFornecedor(fornecedor);

        if (Objects.isNull(validFornecedor)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Fornecedor updatedFornecedor = fornecedorService.Edit(validFornecedor);
        return ResponseEntity.ok(updatedFornecedor);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        Optional<Fornecedor> fornecedorToDelete = fornecedorService.findById(id);
        if (!fornecedorToDelete.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        fornecedorService.Delete(fornecedorToDelete.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private Fornecedor validateFornecedor(Fornecedor fornecedor) {
        if (Objects.isNull(fornecedor) || 
            Objects.isNull(fornecedor.getRazaoSocial()) || 
            Objects.isNull(fornecedor.getNomeFantasia()) || 
            Objects.isNull(fornecedor.getCnpj()) || 
            Objects.isNull(fornecedor.getEmail()) || 
            Objects.isNull(fornecedor.getTelefone())) {
            return null;
        }
        return fornecedor;
    }
}
