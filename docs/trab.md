# Requisitos

- crud
- regra de negocio
- 4 classes no min (2 pacotes e uma heranca no min)
- uml
- divisao de tarefas e github
- trazer tudo cadastrado para testar no dia
- controle de acesso (visoes diferentes)


tarefas:
- criar conta do trello pra glr
- descrver tema
- classes: isa ou lucas
- mockup: gabriel rodrigues
- tratamento de erros (se der): thiago


ordem:
- 1. descrever
- 2. diagrama de classes
- 3. mockup


dados:
- usuarios: nome, e cargo (atendente ou admin (codigo))
- produtos: nome, preco unitario venda e custo, categoria, perecivel*
- itemPedido: produto, quantidade
- pedido: itemPedido, vendedor, preco total, data e hora

funcionalidade:
- gerar recibo de pedido


# Regra de negócio

O sistema possui dois tipos usuários: atendente ou administrador. Os atendentes podem gerar pedidos, usando os produtos já cadastrados. Os adminstradores possuem um código de autorização e podem gerenciar produtos, usuários e gerar e cancelar pedidos. 

Para um atendente criar um pedido, um administrador deverá efetuar a abertura de caixa usando seu código de autorização e informar qual atendente irá utilizar o caixa. Ao final do expediente, um administrador poderá fechar o caixa e verificar qual o saldo restante no caixa.

O sistema poderá aceitar dois tipos de pagamentos para os pedidos: cartão, pix, ou dinheiro em espécie. Caso o método de pagamento seja "cartão", o lucro da venda deverá ser reduzido em 5%.


