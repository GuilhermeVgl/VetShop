package br.com.apirest.vetshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.vetshop.model.Categoria;
import br.com.apirest.vetshop.repository.ICategoriaRepository;

@Service
public class CategoriaService implements IBaseServico<Categoria> {

  @Autowired
  private ICategoriaRepository repository;

  @Override
  public List<Categoria> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Categoria> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Categoria Edit(Categoria obj) {
    Optional<Categoria> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      return this.repository.save(obj);
    }

    return null;
  }

  @Override
  public Categoria Create(Categoria obj) {
    return repository.save(obj);
  }

  @Override
  public Categoria Delete(Categoria obj) {
    Optional<Categoria> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      this.repository.delete(obj);
      return old.get();
    }
      
    return null;
  }
  
}
