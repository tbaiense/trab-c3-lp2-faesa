/**
 * Classe responsável pela interface de terminal do atendente.
 * Controla o fluxo de abertura, fechamento de caixa e listagem de produtos.
 *
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package sistema.interfaces;

import java.util.Scanner;

import sistema.modelos.Admin;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Loja;

public class TelaAtendenteAbrirCaixa {

	private static Scanner scan = new Scanner(System.in);
	private static Caixa caixaAtual = null;
	private static int opcao = 0;
	private static final int OPCAO_SAIR = 5;

	/**
	 * Exibe o menu principal do atendente e processa a escolha do usuário.
	 */
	public static void menuTelaAtendente() {
	    do {
			// Renderização visual do menu no console
			System.out.println("\n"+"=".repeat(26)+"TELA INICIAL "+"=".repeat(27));
			System.out.println(
			    (
					!Loja.existeCaixaAberto()
               	        ? "[1] [ABRIR CAIXA]\n"
                        : "[2] [ACESSAR CAIXA ABERTO]\n"
				) +
				"[3] [LISTAR PRODUTOS]\n"+
				(Loja.getContaLogada() instanceof Admin ? "[4] [PAINEL DO ADMIN]\n" : "\n") +
				String.format("[5] [FINALIZAR PROGRAMA]", OPCAO_SAIR)
			);

			System.out.print("Opção: ");
			opcao = scan.nextInt();

			// Direcionamento do fluxo com base na opção escolhida
			switch (opcao) {
			case 1:
				// Só permite abrir se não houver outro caixa ativo no sistema
				if(!Loja.existeCaixaAberto()) {
					abrirCaixa();
					TelaCaixaPedidos.menuTelaCaixa(); // Redireciona para a tela do caixa
				} else {
					System.out.println("=".repeat(20)+"[CAIXA] Existe um caixa aberto"+"=".repeat(20));
					menuTelaAtendente(); // Recarrega o menu
				}
				break;

			case 2:
				// Só permite fechar se houver um caixa ativo
				if (Loja.existeCaixaAberto()) {
                    TelaCaixaPedidos.menuTelaCaixa();
				} else {
					System.out.println("=".repeat(18)+"[CAIXA] Não existe um caixa aberto"+"=".repeat(18));
					menuTelaAtendente();
				}
				break;

			case 3:
				// Exibe a lista de produtos cadastrados no catálogo
				System.out.println(CatalogoProdutos.getProdutos());
				menuTelaAtendente();
				break;
			case 4:
			    if (Loja.getContaLogada() instanceof Admin) {
					TelaAdminOpcoes.iniciar();
				} else {
                    System.out.println("ERRO: Você não tem acesso a essa funcionalidade.");
				}
			    break;
			case OPCAO_SAIR:
				// Encerra a execução da aplicação
				System.out.println("=".repeat(21)+"[SISTEMA] Programa finalizado"+"=".repeat(20));
				System.exit(0);
				break;

			default:
				// Tratamento para opções numéricas fora do escopo [1-4]
				System.out.println("=".repeat(24)+"[CAIXA] Opção inválida"+"=".repeat(24));
				menuTelaAtendente();
			}
		} while (opcao != OPCAO_SAIR);
	}

	/**
	 * Realiza o processo de abertura de caixa, exigindo autenticação de um Administrador.
	 */
	public static void abrirCaixa() {
		//Pede código de autorização do admin
		TelaAdminAutorizacao.adminAutorizaTela();

		// Definição do saldo inicial para a abertura do caixa
		System.out.println("=".repeat(19)+"[PAINEL] Insira o saldo inicial"+"=".repeat(20));
		System.out.print("Saldo: ");
		double dinheiroInicial = scan.nextDouble();

		// Efetiva a abertura do caixa no sistema da loja
		Loja.abrirCaixa(dinheiroInicial);
	}

	/**
	 * Realiza o fechamento do caixa atual e exibe o saldo final acumulado.
	 */
	public static void fecharCaixa() {
		//Pede código de autorização do admin
		TelaAdminAutorizacao.adminAutorizaTela();
		// Recupera a instância do caixa ativo
		caixaAtual = Loja.getCaixaAtual();

		// Exibe o relatório de fechamento formatado com duas casas decimais
		System.out.println(String.format(
				"[Caixa: Fechamento] Dinheiro em caixa: R$ %.2f\n",
				caixaAtual.getDinheiroFinal()
				));

		// Finaliza o status do caixa no sistema
		Loja.fecharCaixaAtual();
		System.out.println("=".repeat(18)+"[Caixa] caixa fechado com sucesso"+"=".repeat(19));
	}

}
