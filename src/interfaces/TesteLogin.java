package interfaces;

import modelos.Admin;
import modelos.Atendente;

public class TesteLogin {
	
	    public static void main(String[] args) {
	        
	       
	        Atendente meuAtendente = new Atendente("999", "Carlos", 123456);
	        
	        System.out.println("=== DADOS DO ATENDENTE ===");
	        System.out.println("Nome: " + meuAtendente.nome);
	        System.out.println("Matrícula: " + meuAtendente.numMatricula);
	        
	        
	        System.out.println("Senha de Login: " + meuAtendente.getSenhaLogin());
	        
	        
	      
	        Admin meuAdmin = new Admin();
	        
	       
	        meuAdmin.nome = "Ana (Gerente)";
	        meuAdmin.numMatricula = 1;
	        
	        
	        meuAdmin.setSenhaLogin(654321);
	        meuAdmin.setCodAutorizacao(8888);

	        System.out.println("\n=== DADOS DO ADMIN ===");
	        System.out.println("Nome: " + meuAdmin.nome);
	        System.out.println("Senha de Login: " + meuAdmin.getSenhaLogin());
	        
	       
	        boolean testeSucesso = meuAdmin.autorizar(8888);
	        boolean testeFalha = meuAdmin.autorizar(1111);
	        
	        System.out.println("Tentou autorizar com código 8888 (Correto): " + testeSucesso);
	        System.out.println("Tentou autorizar com código 1111 (Errado): " + testeFalha);
	    }
	}

