-- Categorias
INSERT INTO categoria (nome) VALUES ('Medicamentos');
INSERT INTO categoria (nome) VALUES ('Suplementos');
INSERT INTO categoria (nome) VALUES ('Curativos');
INSERT INTO categoria (nome) VALUES ('Rações');
INSERT INTO categoria (nome) VALUES ('Acessórios');

-- Produtos
INSERT INTO produto (preco, categoria_id, nome) VALUES (10.0, 1, 'Vermífugo ABC');
INSERT INTO produto (preco, categoria_id, nome) VALUES (15.0, 1, 'Antibiótico XYZ');
INSERT INTO produto (preco, categoria_id, nome) VALUES (5.0, 2, 'Suplemento DEF');
INSERT INTO produto (preco, categoria_id, nome) VALUES (8.0, 2, 'Vitamina GHI');
INSERT INTO produto (preco, categoria_id, nome) VALUES (7.0, 3, 'Curativo ABC');
INSERT INTO produto (preco, categoria_id, nome) VALUES (12.0, 3, 'Pomada XYZ');
INSERT INTO produto (preco, categoria_id, nome) VALUES (25.0, 4, 'Ração Premium 1kg');
INSERT INTO produto (preco, categoria_id, nome) VALUES (18.0, 4, 'Ração Regular 1kg');
INSERT INTO produto (preco, categoria_id, nome) VALUES (2.0, 5, 'Coleira Simples');
INSERT INTO produto (preco, categoria_id, nome) VALUES (30.0, 5, 'Coleira Anti-Pulgas');

-- Clientes
INSERT INTO cliente (nome, cpf, email, telefone) VALUES ('João Silva', '12345678901', 'joao.silva@example.com', '11999998888');
INSERT INTO cliente (nome, cpf, email, telefone) VALUES ('Maria Souza', '98765432100', 'maria.souza@example.com', '11988887777');
INSERT INTO cliente (nome, cpf, email, telefone) VALUES ('Carlos Pereira', '11122233344', 'carlos.pereira@example.com', '11977776666');

-- Fornecedores
INSERT INTO fornecedor (razao_social, nome_fantasia, cnpj, email, telefone) VALUES ('Fornecedor ABC Ltda', 'Fornecedor ABC', '12345678000199', 'fornecedorabc@example.com', '1133334444');
INSERT INTO fornecedor (razao_social, nome_fantasia, cnpj, email, telefone) VALUES ('Fornecedor XYZ Ltda', 'Fornecedor XYZ', '98765432000188', 'fornecedorxyz@example.com', '1144445555');

-- Pedidos
INSERT INTO pedido (nome, cliente_id, fornecedor_id, data_de_inclusao, data_de_alteracao) VALUES ('Pedido 1', 1, 1, CURRENT_DATE, CURRENT_DATE);

-- Relacionando Produtos com Pedidos
INSERT INTO pedido_produto (pedido_id, produto_id) VALUES (1, 1); 
INSERT INTO pedido_produto (pedido_id, produto_id) VALUES (1, 2); 