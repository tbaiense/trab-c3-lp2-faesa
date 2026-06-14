package interfaces;
import java.util.Scanner;
import sistema.modelos.Admin;
import sistema.modelos.Atendente;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Funcionario;
import sistema.modelos.Loja;
import sistema.services.LoginService;
import sistema.io.Armazenamento;
import sistema.interfaces.*;

public class TelaFuncionarioLogin {

	private static Scanner scan = new Scanner(System.in);
	private static int codAutorizacao = 0;

	public static void main(String[] args) {
		System.out.println("=".repeat(22)+"[SISTEMA] inicializando..."+"=".repeat(22));
		// Inicializa os arquivos do sistema
		Armazenamento.inicializar();

		System.out.println("=".repeat(18)+"[SISTEMA] inicializado com sucesso"+"=".repeat(18));

		iniciar();
	}

	public static void iniciar() {
		String matricula = "";
		String senha = "";
		Funcionario func = null;    // Objeto que armazenará os dados do funcionário logado

		System.out.println("=".repeat(20)+"[TELA DE LOGIN DO FUNCIONÁRIO]"+"=".repeat(20));

		do {
			// Entrada de dados do usuário
			System.out.print("Matrícula: ");
			matricula = scan.next();
			System.out.print("Senha: ");
			senha = scan.next();

			// Verifica se a matrícula e a senha existem no sistema
			if(!LoginService.credenciaisValidas(matricula, senha)) {
				System.out.println("=".repeat(17)+"[Login] Usuário e/ou senha inválidos"+"=".repeat(17));
			}

			// Busca o objeto do funcionário com base nas credenciais fornecidas
			func = LoginService.buscarFuncionario(matricula, senha);

		}while(!LoginService.credenciaisValidas(matricula, senha));

		Loja.inicializar(func);
		CatalogoProdutos.carregarProdutos();

		System.out.println("=".repeat(16)+"[Login] Funcionário logado com sucesso"+"=".repeat(16));

		if(func instanceof Admin) {
			Admin admin = (Admin)func;
			TelaAdminAutorizacao.adminAutoriza(admin.numMatricula);
		}else if(func instanceof Atendente) {
			TelaAtendenteAbrirCaixa.menuTelaAtendente();
		}
	}
}
