package br.com.apirest.vetshop.controller;

import br.com.apirest.vetshop.model.Cliente;
import br.com.apirest.vetshop.service.ClienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) throws BadRequestException {
        cliente = validateCliente(cliente);

        if (Objects.isNull(cliente)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Cliente newCliente = clienteService.Create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> existingCliente = clienteService.findById(id);
        if (!existingCliente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        cliente.setId(id);
        Cliente validCliente = validateCliente(cliente);

        if (Objects.isNull(validCliente)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Cliente updatedCliente = clienteService.Edit(validCliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        Cliente clienteToDelete = new Cliente();
        clienteToDelete.setId(id);
        Cliente deletedCliente = clienteService.Delete(clienteToDelete);

        if (deletedCliente != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Cliente validateCliente(Cliente cliente) {
        if (Objects.isNull(cliente) || 
            Objects.isNull(cliente.getNome()) || 
            Objects.isNull(cliente.getCpf()) || 
            Objects.isNull(cliente.getEmail()) || 
            Objects.isNull(cliente.getTelefone())) {
            return null;
        }
        return cliente;
    }
}
