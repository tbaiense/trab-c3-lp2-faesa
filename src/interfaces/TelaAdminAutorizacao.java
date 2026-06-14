/**
 * Classe de interface de usuário (Console) para a validação de credenciais administrativas.
 * Atua como uma camada interna de segurança, fornecendo métodos de interceptação 
 * e autenticação baseados em matrícula e código de autorização de Administradores.
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package interfaces;

import java.util.Scanner;
import sistema.modelos.Admin;
import sistema.modelos.Loja;

public class TelaAdminAutorizacao {
	
	private static Scanner scan = new Scanner(System.in);
	private static int codAutorizacao;
	
	/**
	 * Valida o código de autorização de um administrador previamente identificado.
	 * Mantém o usuário em loop até que o código correto correspondente à matrícula seja inserido.
	 * * @param numMatricula Matrícula do administrador que está tentando autorizar a ação.
	 */
	public static void adminAutoriza(int numMatricula) {
		// Recupera o objeto Admin correspondente à matrícula fornecida
		Admin admin = Loja.buscarAdmin(numMatricula);
		
		// Loop de validação do código de acesso
		do {
			System.out.print("Código autorização: ");
			codAutorizacao = scan.nextInt();	
			
			// Alerta o usuário caso o código não seja equivalente ao do Admin encontrado
			if(admin.getCodAutorizacao() != codAutorizacao) {
				System.out.println("=".repeat(15)+"[AUTORIZAÇÃO] Código de acesso inválido."+"=".repeat(15));
			}

		} while(admin.getCodAutorizacao() != codAutorizacao); // Repete enquanto a autenticação falhar

		System.out.println("=".repeat(14)+"[AUTORIZAÇÃO] Código de autorização válido"+"=".repeat(14));
	}
	
	/**
	 * Interface visual autônoma que solicita e valida tanto a matrícula quanto o código do administrador.
	 * Utilizada para interceptar operações restritas que exigem elevação de privilégio imediata.
	 */
	public static void adminAutorizaTela() {
		int numMatricula = 0;
		int codAutorizacao = 0;
		Admin admin = null;

		// Loop principal: Garante que o fluxo só prossegue se o admin e o código estiverem corretos
		do {
			// Sub-loop: Garante a busca por uma matrícula de administrador válida no sistema
			do {
				// TODO: finalizar o tratamento da matrícula (ex: tratar InputMismatchException)
				System.out.println("=".repeat(17)+"[PAINEL] Insira a matrícula do admin"+"=".repeat(18));
				System.out.print("Matrícula: ");
				numMatricula = scan.nextInt();
				
				// Busca o administrador no sistema da loja baseado na matrícula informada
				admin = Loja.buscarAdmin(numMatricula);
				System.out.println("=".repeat(11)+"[PAINEL] Insira o código de autorização do admin"+"=".repeat(11));
				
				// Alerta caso nenhum administrador seja encontrado com aquela matrícula
				if(admin == null) {
					System.out.println("=".repeat(21)+"[PAINEL] Matrícula inválida"+"=".repeat(22));	
				}
			} while(admin == null); // Repete a solicitação enquanto a matrícula não existir
			
			// Validação do código de autorização do administrador que foi encontrado com sucesso
			System.out.print("Código de autorização: ");
			codAutorizacao = scan.nextInt();
			
			if(admin.getCodAutorizacao() == codAutorizacao) {
				System.out.println("=".repeat(24)+"[PAINEL] Código válido"+"=".repeat(24));	
			} else {
				System.out.println("=".repeat(23)+"[PAINEL] Código inválido"+"=".repeat(23));
			}
		} while(admin.getCodAutorizacao() != codAutorizacao); // Repete o loop completo se o código estiver incorreto
	}
	
}