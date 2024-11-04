package br.com.apirest.vetshop.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String nome;
  
  @ManyToOne()
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne()
  @JoinColumn(name = "fornecedor_id")
  private Fornecedor fornecedor;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<PedidoProduto> produtos = new HashSet<>();

  @Column(name = "data_de_inclusao")
  private LocalDate dataDeInclusao;

  @Column(name = "data_de_alteracao")
  private LocalDate dataDeAlteracao;
}
