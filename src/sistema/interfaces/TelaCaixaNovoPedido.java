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
import sistema.modelos.Loja;
import sistema.modelos.Pedido;

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

		// Renderização visual das opções do painel de vendas
		System.out.println("\n"+"=".repeat(28)+"TELA DE CAIXA"+"=".repeat(29));
		System.out.println("[1] [ADICIONAR PRODUTO]\n"+
				"[2] [LISTAR PRODUTOS]\n"+
				"[3] [CANCELAR PEDIDO]\n"+
				"[4] [CONCLUIR PEDIDO]\n"+
				"[5] [CANCELAR ITEM]\n"+
				"[6] [FECHAR CAIXA]\n");

		System.out.print("Opção: ");
		opcao = scan.nextInt();

		// Processamento das operações internas da venda
		switch (opcao) {
		case 1:
			// TODO: implementar assim que feito o método
			break;

		case 2:
			// Exibe os produtos do catálogo e retorna ao fluxo de montagem do pedido
			System.out.println(CatalogoProdutos.getProdutos());
			menuTelaNovoPedido();
			break;

		case 3:
			// TODO: implementar assim que feito o método
			break;

		case 4:
			// TODO: implementar assim que feito o método
			break;

		case 5:
			// Solicita credenciais de administrador antes de permitir o estorno/cancelamento de um item
			TelaAdminAutorizacao.adminAutorizaTela();
			// TODO: implementar assim que feito o método
			break;

		case 6:
			// Valida o status do caixa antes de forçar o redirecionamento para o fechamento
			if (Loja.existeCaixaAberto()) {
				TelaAtendenteAbrirCaixa.fecharCaixa();
				TelaAtendenteAbrirCaixa.menuTelaAtendente();
			} else {
				System.out.println("=".repeat(18)+"[CAIXA] Não existe um caixa aberto"+"=".repeat(18));
				TelaAtendenteAbrirCaixa.menuTelaAtendente();
			}
			break;

		default:
			// Tratamento opcional para manter o menu ativo caso digitem uma opção inválida
			System.out.println("=".repeat(24)+"[CAIXA] Opção inválida"+"=".repeat(24));
			menuTelaNovoPedido();
		}
	}
}
