package br.com.apirest.vetshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.vetshop.model.Cliente;
import br.com.apirest.vetshop.repository.IClienteRepository;

@Service
public class ClienteService implements IBaseServico<Cliente> {

  @Autowired
  private IClienteRepository repository;

  @Override
  public List<Cliente> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Cliente> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Cliente Edit(Cliente obj) {
    Optional<Cliente> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      return this.repository.save(obj);
    }

    return null;
  }

  @Override
  public Cliente Create(Cliente obj) {
    return repository.save(obj);
  }

  @Override
  public Cliente Delete(Cliente obj) {
    Optional<Cliente> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      this.repository.delete(obj);
      return old.get();
    }
      
    return null;
  }
}