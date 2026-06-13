/**
 * Armazena as variáveis principais do sistema e fornece métodos de gerenciamento diversos.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package modelos;

import java.time.LocalDateTime;
import java.util.HashMap;
import services.ContaUsuarioService;

public final class Loja {

    private static HashMap<Integer, Funcionario> usuarios;
    private static Funcionario contaAtual;
    private static Caixa caixaAtual;
    private static HashMap<Integer, Caixa> caixasFechados;


    private Loja() {}

    protected static int carregarContasExistentes() {
        Loja.setUsuarios(ContaUsuarioService.obterTodos());

        return Loja.usuarios.size();
    }

    public static void inicializar(Funcionario funcionarioLogado) {
        usuarios = new HashMap<Integer, Funcionario>();
        contaAtual = funcionarioLogado;
        caixaAtual = null;
        caixasFechados = new HashMap<Integer, Caixa>(); // TODO: popular a partir de arquivos
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

    // TODO: FINALIZAR escrevendo no arquivo
    public static Caixa abrirCaixa(double dinheiroInicial) {
        if (Loja.caixaAtual != null) {
            throw new IllegalStateException(
                "O caixa atual deve ser fechado antes de abrir um novo."
            );
        }

        if (Loja.contaAtual == null) {
            throw new IllegalStateException(
                "Deve haver uma conta logada antes de abrir o caixa"
            );
        }

        Loja.caixaAtual = new Caixa(Loja.contaAtual, dinheiroInicial);
        return Loja.caixaAtual;
    }

    // TODO: finalizar escrevendo no arquivo
    public static boolean fecharCaixaAtual() {
        if (Loja.caixaAtual == null) {
            return false;
        }

        Caixa c = Loja.caixaAtual;

        // TODO: remover caixa de arquivo caixaAtual.csv
        Loja.caixaAtual = null;

        c.setFechadoEm(LocalDateTime.now());

        //TODO: calcular total de pagamentos (talvez fazer um método em caixa)

        Loja.caixasFechados.put(c.getId(), c);
        return true;
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
            Loja.usuarios.put(f.numMatricula, f);
        }
    }
}
