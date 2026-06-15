package sistema.interfaces;

import java.util.Scanner;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Produto;

public class TelaProdutoCrud {
	private static Scanner scan = new Scanner(System.in);
	private static int opcao = 0;
	private static final int OPCAO_VOLTAR = 6;

	public static void iniciar() {
	    do {
			System.out.println("\n"+"=".repeat(26)+"TELA DE PRODUTOS"+"=".repeat(27));
			System.out.println("[1] [CADASTRAR PRODUTO]\n"+
					"[2] [BUSCAR PRODUTO]\n"+
					"[3] [REMOVER PRODUTO]\n"+
					"[4] [ATUALIZAR PRODUTO]\n"+
					"[5] [LISTAR PRODUTOS]\n\n" +
					String.format("[%d] [VOLTAR]\n", OPCAO_VOLTAR)
			);
			System.out.print("Opção: ");
			opcao = scan.nextInt();

			switch (opcao) {
			case 1:
				cadastrarProduto();
				break;
			case 2:
				buscarProduto();
				break;
			case 3:
				removerProduto();
				break;
			case 4:
				atualizarProduto();
				break;
			case 5:
				System.out.println(CatalogoProdutos.getProdutos());
				break;
			case OPCAO_VOLTAR:
			    return;
			default:
				System.out.println("Opção inválida");
			}
		} while(opcao != OPCAO_VOLTAR);
	}

	public static void cadastrarProduto() {
		System.out.println("=".repeat(20)+"[PRODUTO] Cadastro de produto"+"=".repeat(20));

		scan.nextLine();
		System.out.print("Nome: ");
		String nome = scan.nextLine();
		System.out.print("Preço de custo: ");
		double precoCusto = scan.nextDouble();
		System.out.print("Preço de venda: ");
		double precoVenda = scan.nextDouble();

		Produto p = new Produto();
		p.setNome(nome);
		p.setPrecoCusto(precoCusto);
		p.setPrecoVenda(precoVenda);

		if (CatalogoProdutos.cadastrar(p)) {
			System.out.println("=".repeat(18)+"[PRODUTO] Cadastrado com sucesso"+"=".repeat(18));
		} else {
			System.out.println("=".repeat(18)+"[PRODUTO] Falha ao cadastrar produto"+"=".repeat(18));
		}
	}

	public static void buscarProduto() {
		System.out.println("=".repeat(20)+"[PRODUTO] Busca de produto"+"=".repeat(20));

		System.out.print("ID: ");
		int id = scan.nextInt();

		Produto p = CatalogoProdutos.buscar(id);
		if (p != null) {
			System.out.println(p);
		} else {
			System.out.println("=".repeat(20)+"[PRODUTO] Produto não encontrado"+"=".repeat(20));
		}
	}

	public static void removerProduto() {
		System.out.println("=".repeat(20)+"[PRODUTO] Remoção de produto"+"=".repeat(20));

		System.out.print("ID: ");
		int id = scan.nextInt();

		Produto removido = CatalogoProdutos.remover(id);
		if (removido != null) {
			System.out.println("=".repeat(18)+"[PRODUTO] Removido com sucesso"+"=".repeat(18));
			System.out.println(removido);
		} else {
			System.out.println("=".repeat(20)+"[PRODUTO] Produto não encontrado"+"=".repeat(20));
		}
	}

	public static void atualizarProduto() {
		System.out.println("=".repeat(18)+"[PRODUTO] Atualização de produto"+"=".repeat(18));

		System.out.print("ID do produto a atualizar: ");
		int id = scan.nextInt();
		scan.nextLine();
		System.out.print("Novo nome: ");
		String nome = scan.nextLine();
		System.out.print("Novo preço de custo: ");
		double precoCusto = scan.nextDouble();
		System.out.print("Novo preço de venda: ");
		double precoVenda = scan.nextDouble();

		Produto atualizado = new Produto(id, nome, precoCusto, precoVenda);
		Produto resultado = CatalogoProdutos.atualizar(id, atualizado);

		if (resultado != null) {
			System.out.println("=".repeat(18)+"[PRODUTO] Atualizado com sucesso"+"=".repeat(18));
			System.out.println(resultado);
		} else {
			System.out.println("=".repeat(20)+"[PRODUTO] Produto não encontrado"+"=".repeat(20));
		}
	}
}
