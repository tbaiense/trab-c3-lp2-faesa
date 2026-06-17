/**
 * Representa um funcionário genérico no sistema.
 * Esta classe atua como a entidade base para todos os usuários da aplicação,
 * armazenando dados essenciais de identificação e credenciais de acesso,
 * como número de matrícula, nome e senha de login. Serve também como superclasse
 * para perfis mais específicos e com maiores privilégios, como Administradores.
 * 
 * @author Gabriel Rodrigues <gabrielrcsj@gmail.com>
 */

package sistema.modelos;


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

	@Override
	public String toString() {
	    return String.format("numMatricula: %d | nome: %s | senhaLogin: %d\n", numMatricula, nome, senhaLogin);
	}
}
