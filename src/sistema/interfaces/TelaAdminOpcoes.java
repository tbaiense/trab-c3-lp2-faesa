package sistema.interfaces;

import java.util.Scanner;

import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Produto;
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
				"[3] [VOLTAR]\n"
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
                return;
    		default:
    			System.out.println("=".repeat(23)+"[ERRO] Opção inválida"+"=".repeat(23));
    		}

		} while (opcao != 3);
	}

	public static void cadastrarAtendente() {
		System.out.println("\n"+"=".repeat(25)+"CADASTRAR ATENDENTE"+"=".repeat(25));

		System.out.print("NOME COMPLETO (nome): ");
		String nome = scan.nextLine();

		var atendente = ContaUsuarioService.cadastrarAtendente(nome);
		System.out.println("=".repeat(15)+"[Sucesso] Atendente cadastrado com sucesso"+"=".repeat(16));
		System.out.println("Detalhes da conta: " + atendente);
	}
}
