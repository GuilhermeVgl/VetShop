package br.com.apirest.vetshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.vetshop.model.Fornecedor;

@Repository
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Long> {
  
}
