package modelos;

public class Atendente extends Funcionario {

   
    public Atendente(String numMatricula, String nome, int senhaLogin) {
        super();
        
        
        try {
            this.numMatricula = Integer.parseInt(numMatricula);
        } catch (NumberFormatException e) {
            System.out.println("Erro: O número de matrícula deve conter apenas números.");
        }
        
        this.nome = nome;
        this.setSenhaLogin(senhaLogin);
    }

	}


