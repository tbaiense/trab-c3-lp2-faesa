/**
 * Representa um usuário com perfil de Atendente no sistema.
 * Esta classe herda da entidade base {@link Funcionario} e é destinada aos
 * colaboradores que operam o caixa, registram pedidos e realizam o atendimento
 * direto ao cliente, não possuindo privilégios administrativos para alterar
 * configurações ou gerenciar a loja.
 * 
 * @author Gabriel Rodrigues <gabrielrcsj@gmail.com>
 */

package sistema.modelos;

public class Atendente extends Funcionario {

    public Atendente(String numMatricula, String nome, int senhaLogin) {
        super(numMatricula, nome, senhaLogin);
    }

	}
