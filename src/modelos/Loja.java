/**
 * Armazena as variáveis principais do sistema e fornece métodos de gerenciamento diversos.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package modelos;

import java.util.HashMap;
import services.ContaUsuarioService;

public final class Loja {

    private static HashMap<Integer, Funcionario> usuarios;
    private static Funcionario contaAtual;
    private static Caixa caixaAtual;

    private Loja() {}

    public static void main(String[] args) {
        System.out.println("Teste!");
    }

    protected static int carregarContasExistentes() {
        Loja.setUsuarios(ContaUsuarioService.obterTodos());

        return Loja.usuarios.size();
    }

    public static void inicializar(Funcionario funcionarioLogado) {
        usuarios = new HashMap<Integer, Funcionario>();
        contaAtual = funcionarioLogado;
        caixaAtual = null;
    }

    public static Funcionario getContaLogada() {
        return Loja.contaAtual;
    }

    public static Admin buscarAdmin(int numMatricula) {
        Funcionario f = Loja.usuarios.get(numMatricula);

        if (f != null && f instanceof Admin) {
            return (Admin) f;
        } else {
            return null;
        }
    }

    public static Funcionario buscarFuncionario(int numMatricula) {
        return usuarios.get(numMatricula);
    }

    // TODO: FINALIZAR
    public static Caixa abrirCaixa(double dinheiroInicial) {
        if (Loja.caixaAtual != null) {
            throw new IllegalStateException(
                "O caixa atual deve ser fechado antes de abrir um novo."
            );
        }

        Loja.caixaAtual = new Caixa(Loja.contaAtual, dinheiroInicial);
        return Loja.caixaAtual;
    }

    // getters e setters =======================================================

    // TODO: FINALIZAR
    private static void setUsuarios(Funcionario[] funcionarios) {
        Loja.usuarios.clear();

        if (funcionarios == null) {
            return;
        }

        Funcionario f;
        for (int i = 0; i < funcionarios.length; ++i) {
            f = funcionarios[i];
            // TODO: mudar assim que colocarem propriedade `funcionario.numMatricula`
            // Loja.usuarios.put(f.numMatricula, f);

            Loja.usuarios.put(i + 1, f);
        }
    }
}
