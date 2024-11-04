package br.com.apirest.vetshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.vetshop.model.Fornecedor;
import br.com.apirest.vetshop.repository.IFornecedorRepository;

@Service
public class FornecedorService implements IBaseServico<Fornecedor> {

  @Autowired
  private IFornecedorRepository repository;

  @Override
  public List<Fornecedor> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Fornecedor> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Fornecedor Edit(Fornecedor obj) {
    Optional<Fornecedor> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      return this.repository.save(obj);
    }

    return null;
  }

  @Override
  public Fornecedor Create(Fornecedor obj) {
    return repository.save(obj);
  }

  @Override
  public Fornecedor Delete(Fornecedor obj) {
    Optional<Fornecedor> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      this.repository.delete(obj);
      return old.get();
    }
      
    return null;
  }
}