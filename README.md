# Sistema de frente de loja (POS)

Este é um trabalho para a disciplina de _Laboratório de Programação II_, para o conceito 3. Os membros do grupo são:

- Gabriel Rodrigues 
- Gabriel Oliveira
- Igor Rios
- Thiago Moura
- Lucas Rodrigues
- Isabela Beraldi _(a confirmar)_ 

# Regra de negócio

O sistema possui dois tipos usuários: atendente ou administrador. Os atendentes podem gerar pedidos, usando os produtos já cadastrados. Os adminstradores possuem um código de autorização e podem gerenciar produtos, usuários e gerar e cancelar pedidos. 

Para um atendente criar um pedido, um administrador deverá efetuar a abertura de caixa usando seu código de autorização e informar qual atendente irá utilizar o caixa. Ao final do expediente, um administrador poderá fechar o caixa e verificar qual o saldo restante no caixa.

O sistema poderá aceitar dois tipos de pagamentos para os pedidos: cartão, pix, ou dinheiro em espécie. Caso o método de pagamento seja "cartão", o lucro da venda deverá ser reduzido em 5%.
