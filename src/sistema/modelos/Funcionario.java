package sistema.modelos;

//meu
public class Funcionario {

	public int numMatricula;
	public String nome;
	private int senhaLogin;

	public Funcionario(String numMatricula, String nome, int senhaLogin) {
    	try {
            this.numMatricula = Integer.parseInt(numMatricula);
        } catch (NumberFormatException e) {
            System.out.println("Erro: O número de matrícula deve conter apenas números.");
        }

        this.nome = nome;
        this.setSenhaLogin(senhaLogin);
	}

	public int getSenhaLogin() {
		return senhaLogin;
	}

	public void setSenhaLogin(int senhaLogin) {
		this.senhaLogin = senhaLogin;
	}

	// TODO: FINALIZAR
	@Override
	public String toString() {
	    return String.format("numMatricula: %d | nome: %s | senhaLogin: %d\n", numMatricula, nome, senhaLogin);
	}
}
