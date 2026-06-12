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

        // TODO: descomentar assim que implementarem propriedades e métodos de modelos.Funcionario
        // for (var f : contas) {
        //     if (f.getNumMatricula() == numMatricula && f.getSenha() == senha) {
        //         return true;
        //     }
        // }

        return false;
    }
}
