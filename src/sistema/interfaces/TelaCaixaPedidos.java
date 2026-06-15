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

import sistema.interfaces.TelaAtendenteAbrirCaixa;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Loja;

public class TelaCaixaPedidos {

	private static Scanner scan = new Scanner(System.in);
	private static Caixa caixaAtual = Loja.getCaixaAtual();
	private static int opcao = 0;

	/**
	 * Exibe o menu de operações do caixa ativo e gerencia a escolha do atendente.
	 */
	public static void menuTelaCaixa() { // TODO: implementar do-while e remover chamadas para menuTelaCaixa()
		// Renderização visual do menu de gerenciamento de pedidos
		System.out.println("\n"+"=".repeat(28)+"TELA DE CAIXA"+"=".repeat(29));
		System.out.println("[1] [ABRIR NOVO PEDIDO]\n"+
				"[2] [LISTAR PRODUTOS]\n"+
				"[3] [VER PEDIDOS ANTIGOS]\n"+
				"[4] [FECHAR CAIXA]");

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
			menuTelaCaixa();
			break;

		case 3:
			// TODO: Corrigir assim que for implementado
			// Exibe o histórico de pedidos já finalizados nesta sessão de caixa
			System.out.println(Arrays.toString(caixaAtual.getPedidosAntigos()));
			menuTelaCaixa();
			break;

		case 4:
			// Verifica o status do caixa antes de acionar a tela de fechamento
			if (Loja.existeCaixaAberto()) {
                TelaAtendenteAbrirCaixa.fecharCaixa();

				TelaAtendenteAbrirCaixa.menuTelaAtendente(); // Retorna para o menu principal do atendente
			} else {
				System.out.println("=".repeat(18)+"[CAIXA] Não existe um caixa aberto"+"=".repeat(18));
				TelaAtendenteAbrirCaixa.menuTelaAtendente();
			}
			break;

		default:
			// Tratamento para entradas numéricas inválidas no menu do caixa
			System.out.println("=".repeat(24)+"[CAIXA] Opção inválida"+"=".repeat(24));
			menuTelaCaixa();
		}
	}

	/**
	 * Verifica as condições do caixa e inicia a criação de um novo pedido.
	 */
	public static void abrirNovoPedido() {
		// Só permite criar um novo pedido se o atual já tiver sido fechado/processado
		if(!caixaAtual.possuiPedidoAtual()) {
			System.out.println("=".repeat(22)+"[CAIXA] Criado novo pedido"+"=".repeat(22));
			TelaCaixaNovoPedido.menuTelaNovoPedido(); // Redireciona para a interface de montagem do pedido
		} else {
			// Bloqueia a criação caso já exista uma venda em andamento no mesmo caixa
			System.out.println("=".repeat(16)+"[CAIXA] Já possui um pedido em aberto"+"=".repeat(17));
			menuTelaCaixa();
		}
	}
}
