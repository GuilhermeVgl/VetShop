package br.com.apirest.vetshop.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.vetshop.model.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

  void deleteAllByFornecedorId(Long fornecedorId);

  Set<Pedido> findByFornecedorId(Long fornecedorId);
}
