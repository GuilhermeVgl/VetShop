package br.com.apirest.vetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apirest.vetshop.model.Pedido;
import br.com.apirest.vetshop.model.PedidoProduto;

public interface IPedidoProdutoRepository extends JpaRepository<PedidoProduto, Long>  {
  void deleteByPedido(Pedido pedido);

  List<PedidoProduto> findByPedidoId(Long pedidoId);

  void deleteByPedidoId(Long pedidoId);
}
