package br.com.apirest.vetshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IBaseServico<T> {

  public List<T> findAll();   
  public Optional<T> findById(Long id);
  public T Edit(T obj);
  public T Create(T obj);
  public T Delete(T obj);

}
