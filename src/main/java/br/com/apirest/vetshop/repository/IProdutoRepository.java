package br.com.apirest.vetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.vetshop.model.Produto;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {
  List<Produto> findByIdIn(List<Long> ids);
}
