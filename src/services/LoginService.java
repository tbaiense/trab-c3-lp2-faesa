/** Gerencia a verificação de credenciais para o login.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package services;

import modelos.Funcionario;

public class LoginService {

    /** Verifica se as credenciais correspondem a um registro de conta. */
    public static boolean credenciaisValidas(int numMatricula, int senha) {
        Funcionario[] contas = ContaUsuarioService.obterTodos();

        for (var f : contas) {
            if (f.numMatricula == numMatricula && f.getSenhaLogin() == senha) {
                return true;
            }
        }

        return false;
    }
}
