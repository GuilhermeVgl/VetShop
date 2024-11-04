package br.com.apirest.vetshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apirest.vetshop.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
  
}
