# Documento de requisitos

<details>
<summary>Lista de requisitos funcionais:</summary>

- Login de conta de usuário
- Cadastro de conta de atendente
- Cadastro de produto
- Cadastro de pedido
- Abertura de caixa
- Fechamento de caixa
- Listagem de produtos
- Listagem de pedidos
- Atualização de produto
- Atualização de pedido
- Remoção de produto
- Cancelamento de pedido
- Salvamento em arquivo
</details>

<details>
<summary>Lista de requisitos não funcionais:</summary>

- Conta inicial de administrador
</details>

Atualizações:

| Versão | Descrição | Autores |
|:-------:| -------- | :-------: |  
| 0.1 | Adiciona resumo dos requisitos funcionais | Thiago M. Baiense |
| 0.2  | 1) Adiciona descrição dos requisitos; 2) Remove categoria de produto conforme decisão do grupo. | Thiago M. Baiense |
## Termos usados

A seguir, temos a definição de alguns termos usados com frequência neste documento:

- **Conta de usuário**: registro que identifica e permite que uma pessoa utilize o sistema
- **Atendente**: conta de usuário com privilégios de atendente
- **Administrador**: conta de usuário com privilégios de administrador
- **Pedido**: registro que representa uma compra finalizada ou não que contém um ou mais produtos
- **Produto**: item comercializável cadastrado no sistema

# Requisitos funcionais

A seguir serão detalhados os requisitos funcionais.

## Login de conta de usuário

O sistema deverá permitir que o usuário acesse as demais funcionalidades somente após este inserir seu número de matrícula e a senha correspondente. O sistema deverá verificar se existe um registro com as informações fornecidas e permitir o acesso ao restante do sistema caso as credenciais sejam válidas. Caso a matrícula não seja encontrada ou a senha seja incorreta para a matrícula em questão, o sistema deverá impedir o acesso às demais funções e exibir uma mensagem de erro.

> **SUGESTÃO (THIAGO)**: O sistema deverá também verificar se há algum caixa aberto após confirmar que as credenciais são válidas. Caso haja um caixa aberto, o sistema deverá permitir o acesso somente se o caixa aberto esteja associado à conta de usuário associada às credenciais inseridas.

Dados solicitados:
- matrícula: String
- senha: String

**Resultado gerado**: permitir ou impedir o acesso ao restante do sistema com base nas credenciais.

## Cadastro de conta de atendente

matricula gerada automaticamente por ordem sequencial. 
senha = cod matricula.

O sistema deverá permitir que um Administrador cadastre novas contas com privilégios de atendente. Para isso, o sistema deverá gerar um código de matrícula com base no número de contas previamente cadastradas, ou seja, um código sequencial. O sistema também deverá registrar uma senha que será exatamente o código de matrícula. 

Dados gerados:

- nova conta de usuário:
  + código de matrícula
  + senha

**Resultado gerado**: sendo o cadastro bem-sucedido, o sistema deverá atualizar sua base de dados armazendo um novo registro de conta de usuário na memória secundária.

## Cadastro de produto

O sistema deverá permir que um Administrador cadastre novos produtos.

Dados solicitados:
- Nome do produto: String
- Preço unitário de custo: double (>= 0)
- Preco unitário de venda: double (>= 0)

**Resultado gerado**: sendo o cadastro bem-sucedido, o sistema deverá atualizar sua base de dados armazendo um novo registro de produto na memória secundária.

## Abertura de caixa



## Cadastro de pedido

Descrição...

## Fechamento de caixa

Descrição...

## Listagem de produtos

Descrição...

## Listagem de pedidos

Descrição...

## Atualização de produto

Descrição...

## Atualização de pedido

Descrição...

## Remoção de produto

Descrição...

## Cancelamento de pedido

Descrição...

# Requisitos não funcionais

## Conta inicial de administrador

> **definido na última reunião**: 
> matricula, senha e cod de autorizacao gerados automaticamente (primeira conta).
