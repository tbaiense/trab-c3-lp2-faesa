package interfaces;

import java.util.Scanner;
import sistema.modelos.Admin;
import sistema.modelos.Loja;

public class TelaAdminAutorizacao {
	private static Scanner scan = new Scanner(System.in);
	private static int codAutorizacao;
	
	public static void adminAutoriza(int numMatricula) {
		Admin admin = Loja.buscarAdmin(numMatricula);
		System.out.println("\n"+"=".repeat(25)+"TELA DE AUTORIZAÇÃO"+"=".repeat(26));
		do {
			System.out.print("Código autorização: ");
			codAutorizacao = scan.nextInt();	
			
			if(admin.getCodAutorizacao() != codAutorizacao) {
				System.out.println("=".repeat(15)+"[AUTORIZAÇÃO] Código de acesso inválido."+"=".repeat(15));
			}

		}while(admin.getCodAutorizacao() != codAutorizacao);

		System.out.println("=".repeat(14)+"[AUTORIZAÇÃO] Código de autorização válido"+"=".repeat(14));

	}
}
