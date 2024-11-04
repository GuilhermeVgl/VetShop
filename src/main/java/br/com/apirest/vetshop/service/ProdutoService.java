package br.com.apirest.vetshop.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apirest.vetshop.model.Produto;
import br.com.apirest.vetshop.repository.IProdutoRepository;

@Service
public class ProdutoService implements IBaseServico<Produto> {

  @Autowired
  private IProdutoRepository repository;

  @Override
  public List<Produto> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Produto> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Produto Edit(Produto obj) {
    Optional<Produto> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      return this.repository.save(obj);
    } else {
        return null;
    }
  }

  @Override
  public Produto Create(Produto obj) {
    return repository.save(obj);
  }

  @Override
  public Produto Delete(Produto obj) {
    Optional<Produto> old = this.repository.findById(obj.getId());

    if (old.isPresent()){
      this.repository.delete(obj);
      return old.get();
    } else {
      return null;
    }
  }

  public List<Produto> findByIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
        return Collections.emptyList();
    }
    return repository.findByIdIn(ids);
  }
  
}
