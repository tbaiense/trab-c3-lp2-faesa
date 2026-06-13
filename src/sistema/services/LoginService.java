/** Gerencia a verificação de credenciais para o login e permite obter contas
 *  durante a etapa de login.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package sistema.services;

import sistema.modelos.Funcionario;

public class LoginService {

    /** Verifica se as credenciais correspondem a um registro de conta. */
    public static boolean credenciaisValidas(String numMatricula, String senhaLogin) {
        return buscarFuncionario(numMatricula, senhaLogin) != null;
    }

    public static Funcionario buscarFuncionario(String numMatricula, String senhaLogin) {
        Funcionario[] contas = ContaUsuarioService.obterTodos();

       if (contas == null) {
           return null;
       }

        try {
            for (var f : contas) {
                if (f.numMatricula == Integer.parseInt(numMatricula)
                    && f.getSenhaLogin() == Integer.parseInt(senhaLogin)) {
                    return f;
                }
            }
        } catch (NumberFormatException ex) {
            System.err.println(
                "[services.LoginService.buscarFuncionario(...)] \n"
                + "\t-> [ERRO] "
                + "Não foi possível converter credenciais em inteiro"
            );
        }

        return null;
    }
}
