package br.com.apirest.vetshop.DTO;

import java.time.LocalDate;
import java.util.List;

import br.com.apirest.vetshop.model.Cliente;
import br.com.apirest.vetshop.model.Fornecedor;
import br.com.apirest.vetshop.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuscarPedidoDTO {
    private Long id;
    private String nome;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private List<Produto> produtos;
    private LocalDate dataDeInclusao;
    private LocalDate dataDeAlteracao;
}
