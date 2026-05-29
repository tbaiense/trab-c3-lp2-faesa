# Sistema de frente de loja (POS)

Este é um trabalho para a disciplina de _Laboratório de Programação II_, para o conceito 3. Os membros do grupo são:

- Gabriel Rodrigues 
- Gabriel Oliveira
- Igor Rios
- Thiago Moura
- Lucas Rodrigues
- Isabela Beraldi

O propósito deste sistema é permitir o gerenciamento de pedidos. Para acessar os requisitos [clique aqui](./docs/requisitos.md).

## Funcionalidades

- Acesso ao sistema usando contas de usuário (login) e cadastro de atendentes pelo administrador.
- Cadastro, visualização, alteração e remoção de produtos
- Cadastro, visualização, atualização e cancelamento de pedidos

## Regra de negócio

### Dados

dados:
- usuarios: nome, e cargo (atendente ou admin (codigo))
- produtos: id, nome, preco unitario venda e custo, ~categoria~ (removido na ultima reuniao)
- itemPedido: produto, quantidade, precoSubTotal (somente no arquivo gerado)
- pedido: itemsPedido[], vendedor, preco total, taxa do cartão, data e hora, forma de pagamento

### Contas de usuário

O sistema possui dois tipos usuários: atendente ou administrador. Os atendentes podem gerar pedidos, usando os produtos já cadastrados. Os adminstradores possuem um código de autorização e podem gerenciar produtos, usuários e gerar e cancelar pedidos. 

Os usuários do tipo `administrador` poderão realizar o cadastro de novas contas do tipo `atendente`.

### Abertura e fechamento de caixa

Para um atendente criar um pedido, este deverá entrar em sua conta e efetuar a abertura de caixa usando o código de autorização do administrador. Ao final do expediente o atendente poderá solicitar o fechamento do caixa, que será concluído mediante o uso do código de autorização de um administrador.

Um atendente NÃO PODERÁ abrir outro caixa até que o caixa atual seja fechado.

### Gerenciamento de pedidos

Com o caixa aberto, um atendente poderá iniciar um novo pedido e adicionar produtos, com suas respectivas quantidades e finalizá-lo mediante o pagamento do cliente. Um caixa NÃO PODERÁ permitir que mais de um pedido seja aberto.

Somente será permitido cancelar pedidos que ainda não foram finalizados (cliente ainda não pagou). O cancelamento só ocorrerá mediante um administrador inserindo seu código de autorização.

O sistema poderá aceitar três tipos de pagamentos para os pedidos: cartão, pix, ou dinheiro em espécie. Caso o método de pagamento seja "cartão", o lucro da venda deverá ser reduzido em 5%.

### Salvamento

O salvamento dos dados ocorrerá mediante a finalização de um pedido e cadastro, atualização ou remoção de um produto, e criação de conta.
