/**
 * Classe de interface de usuário (Console) para a gestão do Caixa de Pedidos.
 * Atua no controle do ciclo de vida das vendas, interligando a camada de modelo
 * às interações diretas do atendente.
 *
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package sistema.interfaces;

import java.util.Arrays;
import java.util.Scanner;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Loja;
import sistema.modelos.Pedido;

public class TelaCaixaPedidos {

    private static Scanner scan = new Scanner(System.in);
    private static int opcao = 0;

    /**
     * Exibe o menu de operações do caixa ativo e gerencia a escolha do atendente.
     */
    public static void menuTelaCaixa() {
        // Renderização visual do menu de gerenciamento de pedidos
        boolean sairTelaCaixa = false;
        do {
        System.out.println("\n" + "=".repeat(28) + "TELA DE CAIXA" + "=".repeat(29));
        System.out.println(
            "[1] [ABRIR NOVO PEDIDO]\n" +
                "[2] [LISTAR PRODUTOS]\n" +
                "[3] [VER PEDIDOS ANTIGOS]\n" +
                "[4] [FECHAR CAIXA]"
        );

        System.out.print("Opção: ");
        opcao = Integer.parseInt(scan.nextLine());

        // Processamento da funcionalidade selecionada
        switch (opcao) {
            case 1:
                // Fluxo para iniciar uma nova venda/pedido
                abrirNovoPedido();
                break;
            case 2:
                // Exibe os produtos do catálogo e retorna ao menu do caixa
                System.out.println(CatalogoProdutos.getProdutos());
                break;
            case 3:
                // Exibe o histórico de pedidos já finalizados nesta sessão de caixa
                exibirPedidosAntigos();
                break;
            case 4:
                // Verifica o status do caixa antes de acionar a tela de fechamento
                if (Loja.existeCaixaAberto()) {
                    TelaAtendenteAbrirCaixa.fecharCaixa();
                    sairTelaCaixa = true;
                } else {
                    System.out.println(
                        "=".repeat(18) + "[CAIXA] Não existe um caixa aberto" + "=".repeat(18)
                    );
                }
                break;
            default:
                // Tratamento para entradas numéricas inválidas no menu do caixa
                System.out.println("=".repeat(24) + "[CAIXA] Opção inválida" + "=".repeat(24));
        }
        } while (!sairTelaCaixa);
    }

    /**
     * Verifica as condições do caixa e inicia a criação de um novo pedido.
     */
    public static void abrirNovoPedido() {
        // Só permite criar um novo pedido se o atual já tiver sido fechado/processado
        if (!Loja.getCaixaAtual().possuiPedidoAtual()) {
            System.out.println("=".repeat(22) + "[CAIXA] Criado novo pedido" + "=".repeat(22));
            TelaCaixaNovoPedido.menuTelaNovoPedido(); // Redireciona para a interface de montagem do pedido
        } else {
            // Bloqueia a criação caso já exista uma venda em andamento no mesmo caixa
            System.out.println(
                "=".repeat(16) + "[CAIXA] Já possui um pedido em aberto" + "=".repeat(17)
            );
        }
    }

    public static void exibirPedidosAntigos() {
        System.out.println(
            "\n" + "=".repeat(20) + "[HISTÓRICO DE PEDIDOS DA SESSÃO]" + "=".repeat(20)
        );

        var historico = Loja.getCaixaAtual().getPedidosAntigos(); // Criação de variável "historico", que chama lista das vendas finalizadas e salvas como "CONCLUÍDO"  até o momento
        if (historico == null || historico.length == 0) {
            // Validação de existência de uma lista de pedidos guardada em historico
            System.out.println("Nenhum pedido finalizado nesta sessão de caixa."); // Caso condição if mostre que não há lista em historico, retorno um informativo ao atendente
        } else {
            for (Pedido p : historico) {
                // Laço For-Each: percorre historico, intem por item, e guarda (temporariamente) cada lista localizada em p
                System.out.println(p.toString()); // Executa toString() customizado da classe Pedido
            }
        }
    }
}
