/**
 * Classe de interface de usuário (Console) destinada às operações do Administrador/Supervisor.
 * Atua como o painel central de gestão do sistema, fornecendo menus interativos para:
 * - Gerenciar o catálogo de produtos (adicionar, editar, remover);
 * - Cadastrar novos usuários/funcionários (Atendentes);
 * - Gerar relatórios financeiros consolidados (Resumo Diário) com base nas movimentações do caixa atual.
 * 
 * @author Gabriel Rodrigues <gabrielrcsj@gmail.com>
 
 */
package sistema.interfaces;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Produto;
import sistema.modelos.Loja; 
import sistema.modelos.Pedido; // Adicionada a importação da classe Pedido
import sistema.services.ContaUsuarioService;

public class TelaAdminOpcoes {
	private static Scanner scan = new Scanner(System.in);
	private static int opcao = 0;

	public static void iniciar() {
	    do {
    	    System.out.println("\n"+"=".repeat(17)+"PAINEL DO ADMIN"+"=".repeat(18));
    		System.out.println(
                "[1] [GERENCIAR PRODUTOS]\n"+
				"[2] [CADASTRAR ATENDENTE]\n" +
				"[3] [RESUMO DIÁRIO]\n" +
				"[4] [VOLTAR]\n"
    		);

    		System.out.print("Opção: ");
    		opcao = scan.nextInt();
    		scan.nextLine();

    		switch (opcao) {
    		case 1:
                TelaProdutoCrud.iniciar();
    			break;
    		case 2:
    			cadastrarAtendente();
    			break;
    		case 3:
                if (Loja.existeCaixaAberto()) {
                    gerarResumoDiario(Loja.getCaixaAtual());
                } else {
                    System.out.println("\n[Aviso] Não há nenhum caixa aberto no momento para gerar o resumo.");
                }
    			break;
    		case 4:
                return;
    		default:
    			System.out.println("=".repeat(23)+"[ERRO] Opção inválida"+"=".repeat(23));
    		}

		} while (opcao != 4);
	}

	public static void cadastrarAtendente() {
		System.out.println("\n"+"=".repeat(25)+"CADASTRAR ATENDENTE"+"=".repeat(25));

		System.out.print("NOME COMPLETO (nome): ");
		String nome = scan.nextLine();

		var atendente = ContaUsuarioService.cadastrarAtendente(nome);
		System.out.println("=".repeat(15)+"[Sucesso] Atendente cadastrado com sucesso"+"=".repeat(16));
		System.out.println("Detalhes da conta: " + atendente);
	}

	public static void gerarResumoDiario(Caixa caixa) {
        String dataHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        // Variáveis para acumular os valores totais
        double totalVendas = 0.0;
        double lucroTotal = 0.0;
        int qtdOk = 0;
        int qtdCancelados = 0;
        
        // Puxa o histórico de pedidos processados no caixa
        Pedido[] historico = caixa.getPedidosAntigos();
        
        // Percorre cada pedido para somar os valores e contar os status
        if (historico != null) {
            for (Pedido p : historico) {
                if (p.getEstado() == Pedido.Estado.CONCLUIDO) {
                    totalVendas += p.getPrecoVendaTotal(); // Puxa método da classe Pedido
                    lucroTotal += p.getLucro();            // Puxa método da classe Pedido
                    qtdOk++;
                } else if (p.getEstado() == Pedido.Estado.CANCELADO) {
                    qtdCancelados++;
                }
            }
        }
        
        System.out.println("\n=== RESUMO DIÁRIO: " + dataHoje + " ===");
        System.out.printf("CAIXA: Inicial [R$ %.2f] -> Final [R$ %.2f]\n", caixa.getDinheiroInicial(), caixa.getDinheiroFinal());
        
        // Exibe os valores calculados acima
        System.out.printf("FINANÇAS: Vendas [R$ %.2f] | Lucro [R$ %.2f]\n", totalVendas, lucroTotal);
        
        System.out.printf("PEDIDOS: [%d] OK | [%d] CANCELADOS\n", qtdOk, qtdCancelados);
        System.out.println("===========================================");
	}
}