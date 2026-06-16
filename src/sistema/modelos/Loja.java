/**
 * Armazena as variáveis principais do sistema e fornece métodos de
 * gerenciamento diversos.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package sistema.modelos;

import java.time.LocalDateTime;
import java.util.HashMap;

import sistema.io.Armazenamento;
import sistema.io.Arquivos;
import sistema.services.ContaUsuarioService;

public final class Loja {

    private static HashMap<Integer, Funcionario> usuarios;
    private static Funcionario contaAtual;
    private static Caixa caixaAtual;
    private static HashMap<Integer, Caixa> caixasFechados;

    static {
        usuarios = new HashMap<Integer, Funcionario>();
        caixasFechados = new HashMap<Integer, Caixa>();
    }

    private Loja() {}

    private static int carregarContasExistentes() {
        Loja.setUsuarios(ContaUsuarioService.obterTodos());
        ContaUsuarioService.descarregarContas(); // liberar memória, uma vez que as pesquisas serão feitas a partir da Loja agora em diante

        return Loja.usuarios.size();
    }

    public static void inicializar(Funcionario funcionarioLogado) {
        if (funcionarioLogado == null) {
            throw new IllegalArgumentException("funcionarioLogado não pode ser null");
        }

        carregarContasExistentes();
        contaAtual = funcionarioLogado;

        caixaAtual = Arquivos.Caixas.ler_caixaAtual();

        Caixa[] cFechados = Arquivos.Caixas.ler_caixasFechados(); // nao le os pedidos associados

        if (cFechados != null && cFechados.length != 0) {
            for (var c: cFechados) {
                 caixasFechados.put(c.getId(), c);
            }
        }
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

        Caixa c = new Caixa(Loja.contaAtual, dinheiroInicial);
        c.setId(Arquivos.Caixas.ler_caixasFechados().length + 1);
        c.setAbertoEm(LocalDateTime.now());

        Loja.caixaAtual = c;
        Arquivos.Caixas.inserir_caixaAtual(c);
        caixaAtual.setPedidosAntigos(null);
        return Loja.caixaAtual;
    }

    public static boolean existeCaixaAberto() {
       return getCaixaAtual() != null;
    }

    public static Caixa getCaixaAtual() {
        if (caixaAtual != null) {
        }
        return Loja.caixaAtual;
    }

    public static boolean fecharCaixaAtual() {
        if (Loja.caixaAtual == null) {
            return false;
        }

        Caixa c = Loja.caixaAtual;

        Loja.caixaAtual = null;
        Arquivos.Caixas.remover_caixaAtual();

        c.setFechadoEm(LocalDateTime.now());

        Loja.caixasFechados.put(c.getId(), c);
        Arquivos.Caixas.inserir_caixaFechado(c);
        return true;
    }

    public static HashMap<Integer, Caixa> getCaixasFechados() {
        var caixas = new HashMap<Integer, Caixa>();

        Caixa[] carregados = Arquivos.Caixas.ler_caixasFechados();

        if (carregados != null) {
            for (var c: carregados) {
                caixas.put(c.getId(), c);
            }
        }

        return caixas;
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
