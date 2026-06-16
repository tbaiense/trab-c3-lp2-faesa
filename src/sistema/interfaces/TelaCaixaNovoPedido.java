/**
 * Classe de interface de usuário (Console) para a criação e manipulação de um Novo Pedido.
 * Gerencia o ciclo de vida da venda atual, controlando fluxos de adição de produtos,
 * cancelamentos parciais/totais com validação do admin e finalização do pedido.
 *
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package sistema.interfaces;

import java.util.Scanner;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.ItemPedido;
import sistema.modelos.Loja;
import sistema.modelos.Pedido;
import sistema.modelos.Produto;

public class TelaCaixaNovoPedido {

    private static Scanner scan = new Scanner(System.in);
    private static Pedido pedidoAtual = null;
    private static Caixa caixaAtual = Loja.getCaixaAtual();
    private static int opcao = 0;

    /**
     * Instancia o novo pedido no caixa ativo e gerencia o menu de construção da venda.
     */
    public static void menuTelaNovoPedido() {
        // Inicializa ou recupera a instância de um novo pedido atrelado ao caixa atual
        pedidoAtual = caixaAtual.novoPedido();
        boolean sairTelaPedido = false;
        // Renderização visual das opções do painel de vendas
        do {
            System.out.println("\n" + "=".repeat(28) + "TELA DE NOVO PEDIDO" + "=".repeat(29));
            System.out.println(
                "[1] [ADICIONAR ITEM]\n" +
                    "[2] [LISTAR PRODUTOS]\n" +
                    "[3] [CANCELAR PEDIDO]\n" +
                    "[4] [CONCLUIR PEDIDO]\n" +
                    "[5] [REMOVER ITEM]\n"
            );

            System.out.print("Opção: ");
            opcao = Integer.parseInt(scan.nextLine());

            // Processamento das operações internas da venda
            switch (opcao) {
                case 1:
                    exibirTelaPedidoAdicionarItem();
                    break;
                case 2:
                    // Exibe os produtos do catálogo e retorna ao fluxo de montagem do pedido
                    System.out.println(CatalogoProdutos.getProdutos());
                    break;
                case 3:
                    sairTelaPedido = exibirTelaPedidoCancelar();
                    break;
                case 4:
                    sairTelaPedido = exibirTelaPedidoConcluir();
                    break;
                case 5:
                    exibirTelaPedidoRemoverItem();
                    break;
                default:
                    // Tratamento opcional para manter o menu ativo caso digitem uma opção inválida
                    System.out.println("=".repeat(24) + "[CAIXA] Opção inválida" + "=".repeat(24));
            }

        } while (!sairTelaPedido);
    }

    public static void exibirTelaPedidoAdicionarItem() {
        // [ADICIONAR PRODUTO]
        // Mantém visual da estrutura
        System.out.println("\n" + "=".repeat(20) + "[ADICIONAR PRODUTO]" + "=".repeat(20));

        // Chama lista de produtos já cadastrados e exibe informações de cada um
        System.out.println(CatalogoProdutos.getProdutos());

        System.out.print("Código do Produto: ");
        int codProd = scan.nextInt();
        scan.nextLine(); // Limpador do buffer do teclado

        // Realiza busca de Produto no catálogo
        // Localiza o que coresonde com o número/ID de produto que atendente informar
        // Retorna correspondente, caso haja um
        Produto produtoEscolhido = CatalogoProdutos.buscar(codProd);

        if (produtoEscolhido != null) {
            // Valida se um produto foi encontrado
            System.out.print("Quantidade desejada: "); // Solicita quantidade de unidades desse mesmo produto encontrado
            int qtd = scan.nextInt();
            scan.nextLine(); // Limpa o buffer do teclado

            // Tendo validação de produto encontrado, valida se quantidade informada >= 1
            // Se quantidade for válida, atualiza a lista de produtos no carrinho
            pedidoAtual.atualizarItem(produtoEscolhido, qtd);
            System.out.println(
                "=".repeat(15) + "[CAIXA] Item processado com sucesso!" + "=".repeat(15)
            );
        } else {
            System.out.println("=".repeat(15) + "[ERRO] Produto não encontrado!" + "=".repeat(15));
        } // Tratamento para casos de produto não encontrado.
    }

    public static boolean exibirTelaPedidoCancelar() {
        // [CANCELAR PEDIDO]
        System.out.println("\n" + "=".repeat(20) + "[CANCELAR PEDIDO]" + "=".repeat(20));
        System.out.print("Deseja realmente cancelar e descartar o pedido atual? (S/N): "); // Ped confirmação da desistência da venda/pedido em aberto
        char confCancela = scan.nextLine().trim().toUpperCase().charAt(0); //.trim() remove possíveis espaços em branco que tenha sido digitados
        // .toUpperCase() transforma letras minúsculas em maiúsculas, caso atendente digite "s" invés de "S"

        if (confCancela == 'S') {
            caixaAtual.cancelarPedidoAtual();
            return true; // Retorna para a tela de menu
        }

        return false;
    }

    public static boolean exibirTelaPedidoConcluir() {
        // [CONCLUIR PEDIDO]
        System.out.println("\n" + "=".repeat(20) + "[CONCLUIR PEDIDO]" + "=".repeat(20));
        if (pedidoAtual.getItens().isEmpty()) {
            // Garante que condição de que haja itens no carrinho para que um pedido possa ser finalizado seja atendida
            System.out.println(
                "=".repeat(15) + "[ERRO] Não é possível concluir um pedido vazio!" + "=".repeat(15)
            );

            return false;
        }

        // Exibe informações do pedido para que atendente possa conferir informações de itens, quantidades e valores.
        System.out.println("\n--- DETALHES DO PEDIDO ---");
        for (ItemPedido ip : pedidoAtual.getItens()) {
            System.out.println(ip.toString());
        }
        System.out.printf("VALOR TOTAL: R$ %.2f\n", pedidoAtual.getPrecoVendaTotal()); // Aciona método para calcular valor total da venda e exibe resultado
        System.out.println("-".repeat(26));

        System.out.print("Selecione a forma de pagamento (DINHEIRO, CARTAO ou PIX): "); // Apresenta opções de pagamento e solicita que atendente infrome escolha do comprador
        String formaPgto = scan.nextLine().trim().toUpperCase();

        try {
            // Roda opções de pagamento e executa a que for informada, conforme definições de funcionamento determinadas
            double troco = 0.0;
            if (formaPgto.equals("DINHEIRO")) {
                System.out.print("Valor entregue pelo cliente: R$ ");
                double valorEntrada = Double.parseDouble(scan.nextLine());


                troco = pedidoAtual.calcularTroco(valorEntrada);
                System.out.printf("TROCO A EMITIR: R$ %.2f\n", troco);

                if (troco > caixaAtual.getDinheiroFinal()) {
                    System.out.printf(
                        "[Pedido] ERRO: não é possível concluir o pedido usando forma de pagamento DINHEIRO, pois não há troco suficiente no caixa. Por favor escolha outro método de pagamento.\n[Caixa] Dinheiro em caixa: R$ %.2f\n\n",
                        caixaAtual.getDinheiroFinal()
                    );

                    return false;
                }

                // Se escolha for DINHEIRO, aciona cálculo de troco
                pedidoAtual.receberPagamento(formaPgto, valorEntrada);
            } else {
                // Se escolha for CARTAO ou PIX, exibe valor a ser pago
                pedidoAtual.receberPagamento(formaPgto, pedidoAtual.getPrecoVendaTotal());
            }

            caixaAtual.concluirPedidoAtual();
            System.out.println("=".repeat(18) + "[SUCESSO] Venda finalizada!" + "=".repeat(18)); // Informa sucesso na finalização da venda
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Valida que valor pago foi o valor da venda. Caso não seja, informa atendente e trava a finalização para que não aconteça sem pagamento válido
            System.out.println(
                "=".repeat(12) + "[ERRO NO PAGAMENTO] " + "=".repeat(12) + "\n" + e.getMessage()
            );

            return false;
        }
    }

    public static void exibirTelaPedidoRemoverItem() {
        // [CANCELAR ITEM]
        // Chama autorização do admin criada para permitir cancelamento de item
        System.out.println(
            "\n" + "=".repeat(20) + "[ESTORNAR ITEM DO PEDIDO]" + "=".repeat(20)
        );

        for (ItemPedido ip : pedidoAtual.getItens()) {
            // Exibe todos os itens registrados no carrinho e as informações de cada um.
            System.out.printf(
                "ID Produto: %d | %s\n",
                ip.getProduto().getId(),
                ip.toString()
            );
        }

        System.out.print("Digite o ID do produto que deseja remover completamente: ");
        int idProdRemover = Integer.parseInt(scan.nextLine()); // Identifica produto que deve ser removido

        pedidoAtual.removerItem(idProdRemover); // Utiliza método removerItem: busca por ID do produto, localiza o que será removido e executa removeIf
        System.out.println(
            "=".repeat(17) + "[SUCESSO] Solicitação de estorno processada." + "=".repeat(17)
        );
    }
}
