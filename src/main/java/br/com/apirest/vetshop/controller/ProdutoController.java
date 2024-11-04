package br.com.apirest.vetshop.controller;

import br.com.apirest.vetshop.model.Categoria;
import br.com.apirest.vetshop.model.Produto;
import br.com.apirest.vetshop.service.CategoriaService;
import br.com.apirest.vetshop.service.ProdutoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) throws BadRequestException {
        produto = validateProduto(produto);

        if (Objects.isNull(produto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Produto newProduto = produtoService.Create(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Optional<Produto> existingProduto = produtoService.findById(id);
        if (!existingProduto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        produto.setId(id);
        Produto validProduto = validateProduto(produto);
    
        if (Objects.isNull(validProduto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        Produto updatedProduto = produtoService.Edit(validProduto);
        return ResponseEntity.ok(updatedProduto);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        Produto produtoToDelete = new Produto();
        produtoToDelete.setId(id);
        Produto deletedProduto = produtoService.Delete(produtoToDelete);

        if (deletedProduto != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Produto validateProduto(Produto produto) {
        if (Objects.isNull(produto) || 
            Objects.isNull(produto.getNome()) || 
            Objects.isNull(produto.getPreco()) ||
            Objects.isNull(produto.getCategoria())) {
            return null;
        }
        Optional<Categoria> categoria = categoriaService.findById(produto.getCategoria().getId());

        if (!categoria.isPresent()) {
            return null;
        }

        produto.setCategoria(categoria.get());
    
        return produto;
    }
}
