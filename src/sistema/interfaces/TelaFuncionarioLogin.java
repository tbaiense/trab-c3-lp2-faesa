/**
 * Classe de interface de usuário (Console) principal para autenticação de funcionários.
 * Atua como o ponto de entrada (Main) do sistema, gerenciando a inicialização de arquivos,
 * validação de credenciais de login e o redirecionamento de telas baseado no nível de acesso (Polimorfismo).
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package sistema.interfaces;

import java.util.Scanner;
import sistema.modelos.Admin;
import sistema.modelos.Atendente;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.Funcionario;
import sistema.modelos.Loja;
import sistema.services.LoginService;
import sistema.io.Armazenamento;

public class TelaFuncionarioLogin {

	private static Scanner scan = new Scanner(System.in);

	/**
	 * Inicializar o teste de telas do sistema
	 */
	public static void main(String[] args) {
		System.out.println("=".repeat(22)+"[SISTEMA] inicializando..."+"=".repeat(22));

		// Inicializa e carrega os arquivos de persistência de dados do sistema (I/O)
		Armazenamento.inicializar();

		System.out.println("=".repeat(18)+"[SISTEMA] inicializado com sucesso"+"=".repeat(18));

		// Invoca o fluxo de login
		iniciar();
	}

	/**
	 * Gerencia o loop de autenticação do funcionário e o direciona para a sua respectiva interface de trabalho.
	 */
	public static void iniciar() {
		String matricula = "";
		String senha = "";
		Funcionario func = null; // Objeto polimórfico que armazenará os dados do usuário autenticado

		System.out.println("=".repeat(20)+"[TELA DE LOGIN DO FUNCIONÁRIO]"+"=".repeat(20));

		// Loop de autenticação: Garante a permanência na tela até que credenciais válidas sejam fornecidas
		do {
			// Entrada de dados do usuário
			System.out.print("Matrícula: ");
			matricula = scan.next();
			System.out.print("Senha: ");
			senha = scan.next();

			// Verifica se o par de matrícula e senha existe nas regras de negócio do LoginService
			if(!LoginService.credenciaisValidas(matricula, senha)) {
				System.out.println("=".repeat(17)+"[Login] Usuário e/ou senha inválidos"+"=".repeat(17));
			}

			// Busca e instancia o objeto do funcionário com base nas credenciais informadas
			func = LoginService.buscarFuncionario(matricula, senha);

		} while(!LoginService.credenciaisValidas(matricula, senha)); // Repete se o login falhar

		// Define o funcionário ativo no contexto global da loja e carrega o catálogo em memória
		Loja.inicializar(func);
		CatalogoProdutos.carregarProdutos();

		// Avalia o tipo do objeto (Polimorfismo) para determinar o fluxo de telas subsequente
		if(func instanceof Admin) {
			// Realiza o Downcasting seguro para acessar atributos específicos de Administrador
			Admin admin = (Admin) func;
			// Exige a verificação do código de segurança do administrador
			TelaAdminAutorizacao.adminAutoriza(admin.numMatricula);
			System.out.println("=".repeat(16)+"[Login] Funcionário logado com sucesso"+"=".repeat(16));
		} else if(func instanceof Atendente) {
			// Redireciona o atendente diretamente para o painel de abertura e controle de caixa
			System.out.println("=".repeat(16)+"[Login] Funcionário logado com sucesso"+"=".repeat(16));
			TelaAtendenteAbrirCaixa.menuTelaAtendente();
		}
	}
}
