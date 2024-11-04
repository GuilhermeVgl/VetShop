package br.com.apirest.vetshop.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apirest.vetshop.DTO.BuscarPedidoDTO;
import br.com.apirest.vetshop.model.Cliente;
import br.com.apirest.vetshop.model.Fornecedor;
import br.com.apirest.vetshop.model.Pedido;
import br.com.apirest.vetshop.model.PedidoProduto;
import br.com.apirest.vetshop.model.Produto;
import br.com.apirest.vetshop.repository.IPedidoProdutoRepository;
import br.com.apirest.vetshop.service.ClienteService;
import br.com.apirest.vetshop.service.FornecedorService;
import br.com.apirest.vetshop.service.PedidoService;
import br.com.apirest.vetshop.service.ProdutoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private IPedidoProdutoRepository pedidoProdutoRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.findAll();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuscarPedidoDTO> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = pedidoService.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();

            List<Produto> produtos = findProdutosByPedidoId(pedido.getId());
    
            BuscarPedidoDTO buscarPedidoDTO = new BuscarPedidoDTO(
                pedido.getId(),
                pedido.getNome(),
                pedido.getCliente(),
                pedido.getFornecedor(),
                produtos,
                pedido.getDataDeInclusao(),
                pedido.getDataDeAlteracao()
            );
    
            return new ResponseEntity<>(buscarPedidoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<Produto> findProdutosByPedidoId(Long pedidoId) {
        List<PedidoProduto> pedidoProdutos = pedidoProdutoRepository.findByPedidoId(pedidoId);
        return pedidoProdutos.stream()
                             .map(PedidoProduto::getProduto)
                             .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        pedido = validatePedido(pedido);

        if (Objects.isNull(pedido)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Pedido newPedido = pedidoService.Create(pedido);
        
        List<Long> produtosPedido = 
                newPedido.getProdutos()
                .stream()
                .map(pedidoProduto -> pedidoProduto.getProduto().getId())
                .collect(Collectors.toList());


        produtosPedido.forEach(item -> {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setPedido(newPedido);
            pedidoProduto.setProduto(produtoService.findById(item).get());
            pedidoProdutoRepository.save(pedidoProduto);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedido.setId(id);
    
        pedido = validatePedido(pedido);
    
        if (Objects.isNull(pedido)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        Optional<Pedido> existingPedidoOptional = pedidoService.findById(id);
        if (existingPedidoOptional.isPresent()) {
            Pedido updatedPedido = pedidoService.Edit(pedido);
    
            pedidoProdutoRepository.deleteByPedidoId(updatedPedido.getId());
    
            List<PedidoProduto> produtosPedido = updatedPedido.getProdutos().stream().toList();
    
            produtosPedido.forEach(item -> {
                item.setPedido(updatedPedido);
                pedidoProdutoRepository.save(item);
            });
    
            return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        BuscarPedidoDTO pedidoToDelete = pedidoService.findByIdDTO(id);
    
        if (Objects.isNull(pedidoToDelete)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        pedidoToDelete.getProdutos().forEach(item -> {
            pedidoProdutoRepository.deleteByPedidoId(pedidoToDelete.getId());
        });
    
        pedidoService.Delete(pedidoService.findById(pedidoToDelete.getId()).get());
    
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }    

    private Pedido validatePedido(Pedido pedido) {
        if (Objects.isNull(pedido) ||
                Objects.isNull(pedido.getNome()) ||
                Objects.isNull(pedido.getCliente()) ||
                Objects.isNull(pedido.getFornecedor()) ||
                Objects.isNull(pedido.getProdutos())) {
            return null;
        }

        Optional<Cliente> cliente = clienteService.findById(pedido.getCliente().getId());
        Optional<Fornecedor> fornecedor = fornecedorService.findById(pedido.getFornecedor().getId());

        if (!cliente.isPresent() || !fornecedor.isPresent()) {
            return null;
        }

        pedido.setCliente(cliente.get());
        pedido.setFornecedor(fornecedor.get());

        for (PedidoProduto pedidoProduto : pedido.getProdutos()) {
            if (Objects.isNull(pedidoProduto.getProduto())) {
                return null;
            }

            Optional<Produto> produto = produtoService.findById(pedidoProduto.getProduto().getId());
            if (!produto.isPresent()) {
                return null;
            }

            pedidoProduto.setProduto(produto.get());
        }

        return pedido;
    }
}