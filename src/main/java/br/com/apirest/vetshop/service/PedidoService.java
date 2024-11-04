package br.com.apirest.vetshop.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.vetshop.DTO.BuscarPedidoDTO;
import br.com.apirest.vetshop.model.Pedido;
import br.com.apirest.vetshop.model.PedidoProduto;
import br.com.apirest.vetshop.model.Produto;
import br.com.apirest.vetshop.repository.IPedidoProdutoRepository;
import br.com.apirest.vetshop.repository.IPedidoRepository;

@Service
public class PedidoService implements IBaseServico<Pedido> {
  
  @Autowired
  private IPedidoRepository repository;

  @Autowired
  private IPedidoProdutoRepository pedidoProdutoRepository;

  @Override
  public List<Pedido> findAll() {
    return repository.findAll();
  }

  public BuscarPedidoDTO findByIdDTO(Long id) {
    Optional<Pedido> pedido = repository.findById(id);

      if(pedido.isPresent()) {
          List<Produto> produtos = findProdutosByPedidoId(pedido.get().getId());
  
          BuscarPedidoDTO buscarPedidoDTO = new BuscarPedidoDTO(
              pedido.get().getId(),
              pedido.get().getNome(),
              pedido.get().getCliente(),
              pedido.get().getFornecedor(),
              produtos,
              pedido.get().getDataDeInclusao(),
              pedido.get().getDataDeAlteracao()
          );
        return buscarPedidoDTO;
      }

    return null;
  }

  public List<Produto> findProdutosByPedidoId(Long pedidoId) {
    List<PedidoProduto> pedidoProdutos = pedidoProdutoRepository.findByPedidoId(pedidoId);
    return pedidoProdutos.stream()
                          .map(PedidoProduto::getProduto)
                          .collect(Collectors.toList());
  }

  @Override
  public Optional<Pedido> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Pedido Edit(Pedido obj) {
    Optional<Pedido> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      return this.repository.save(obj);
    }

    return null;
  }

  @Override
  public Pedido Create(Pedido obj) {
    return repository.save(obj);
  }

  @Override
  public Pedido Delete(Pedido obj) {
    Optional<Pedido> old = this.repository.findById(obj.getId());

    this.repository.delete(obj);
    return old.get();
  }
}
