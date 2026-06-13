package services;

import io.Arquivos;
import modelos.Funcionario;

public class ContaUsuarioService {

    private static Funcionario[] contas = null;

    /** Lê as contas dos arquivos e armazena internamente na classe
     *
     */
    public static void carregarContas() {
        contas = Arquivos.Contas.ler_contas();
    }

    /** Retorna as contas carregadas. Ao criar uma nova conta, será necessário chamar o método {@code carregarContas()} para atualizar os resultados.
    *
    * @return as contas carregadas.
 */
    public static Funcionario[] obterTodos() {
        if (contas == null) {
            carregarContas();
        }

        return contas;
    }
}
