package interfaces;

import java.util.Scanner;

import sistema.modelos.Admin;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Loja;

public class TelaAtendenteAbrirCaixa {

	private static Scanner scan = new Scanner(System.in);
	private static Caixa caixaAtual = null;

	private static int opcao = 0;

	public static void menuTelaAtendente() {
		System.out.println("\n"+"=".repeat(26)+"TELA DE ATENDENTE"+"=".repeat(27));
		System.out.println("[1] [ABRIR CAIXA]\n"+
				"[2] [FECHAR CAIXA]\n"+
				"[3] [LISTAR PRODUTOS]\n");

		System.out.print("Opção: ");
		opcao = scan.nextInt();

		switch (opcao) {
		case 1:
			if(!Loja.existeCaixaAberto()) {
				abrirCaixa();
			}else {
				System.out.println("=".repeat(20)+"[CAIXA] Existe um caixa aberto"+"=".repeat(20));
			}

			break;
		case 2: 
			if (Loja.existeCaixaAberto()) {
				fecharCaixa();
			}else {
				System.out.println("=".repeat(18)+"[CAIXA] Não existe um caixa aberto"+"=".repeat(18));
			}
			break;
		case 3:
			System.out.println(CatalogoProdutos.getProdutos());
		default:

		}
	}

	public static void abrirCaixa() {
		int codAutorizacao = 0;
		Admin admin = null;
		try {
			do {
				System.out.println("=".repeat(17)+"[PAINEL] Insira a matrícula do admin"+"=".repeat(18));
				System.out.print("Matrícula: ");
				int matricula = scan.nextInt();
				admin = Loja.buscarAdmin(matricula);
				System.out.println("=".repeat(11)+"[PAINEL] Insira o código de autorização do admin"+"=".repeat(11));
				System.out.print("Código de autorização: ");
				codAutorizacao = scan.nextInt();
				if(admin.getCodAutorizacao() == codAutorizacao) {
					System.out.println("=".repeat(24)+"[PAINEL] Código válido"+"=".repeat(24));	
				}else {
					System.out.println("=".repeat(23)+"[PAINEL] Código inválido"+"=".repeat(23));
				}
			}while(admin.getCodAutorizacao() != codAutorizacao);
		}catch(Exception e) {
			System.err.println(e);
		}
		System.out.println("=".repeat(19)+"[PAINEL] Insira o saldo incicial"+"=".repeat(20));
		System.out.print("Saldo: ");
		double dinheiroIncicial = scan.nextDouble();
		Loja.abrirCaixa(dinheiroIncicial);

	}

	public static void fecharCaixa() {
		caixaAtual = Loja.getCaixaAtual();
		System.out.println(String.format(
				"[Caixa: Fechamento] Dinheiro em caixa: R$ %.2f\n",
				caixaAtual.getDinheiroFinal()
				));

		Loja.fecharCaixaAtual();
		System.out.println("=".repeat(18)+"[Caixa] caixa fechado com sucesso"+"=".repeat(19));

	}


}
