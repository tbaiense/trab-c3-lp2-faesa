package sistema;
import sistema.modelos.Caixa;
import sistema.modelos.Loja;
import sistema.modelos.Pedido;
import sistema.services.LoginService;

public class ProgramaPrincipal {
    public static void main(String[] args) {
        System.out.println("[SISTEMA] iniciando execução...");
        System.out.println("[SISTEMA] iniciado com sucesso.");

        teste();
    }

    private static void teste() {
        // LOGIN =============================================================
        String numMatricula = "2";
        String senhaLogin = "123";

        if (!LoginService.credenciaisValidas(numMatricula, senhaLogin)) {
            System.out.println("[Login] credenciais inválidas!");
            finalizarExecucao();
        }

        System.out.println("[Login] Logado com sucesso!");

        Loja.inicializar(LoginService.buscarFuncionario(numMatricula, senhaLogin));


        // CAIXA ==============================================================

        // Descomente para simular fechamento de caixa
        // Loja.abrirCaixa(1000.00);

        String strTelaCaixa = (
            "OPCOES -------\n"
            + (
                Loja.existeCaixaAberto()
                ? "1) Fechar caixa atual"
                : "1) Abrir novo caixa"
            ) + "\n\n"
            + ">>> 1"
        );

        System.out.println(strTelaCaixa);

        Caixa caixaAtual;

        if (Loja.existeCaixaAberto()) {
            // Simulando fechamento de caixa =================================
            caixaAtual = Loja.getCaixaAtual();
            System.out.println(String.format(
                "[Caixa: Fechamento] Dinheiro em caixa: R$ %.2f\n",
                caixaAtual.getDinheiroFinal()
            ));

            Loja.fecharCaixaAtual();
            System.out.println("[Caixa] caixa fechado com sucesso!");

            return;
        }

        // Simulando abertura de caixa ========================================
        caixaAtual = Loja.abrirCaixa(0.0);
        System.out.println("\n[Caixa] novo caixa aberto!");

        Pedido pedidoAtual = caixaAtual.novoPedido();

        // TODO: gerenciar pedido

        caixaAtual.concluirPedidoAtual();

        // Fechamento do caixa ================================================
        caixaAtual = Loja.getCaixaAtual();
        System.out.println(String.format(
            "[Caixa: Fechamento] Dinheiro em caixa: R$ %.2f\n",
            caixaAtual.getDinheiroFinal()
        ));

        Loja.fecharCaixaAtual();

        finalizarExecucao();
    }

    public static void finalizarExecucao() {
        System.out.println("[SISTEMA] Finalizando execução...");

        // TODO: descarregar dados, salvar informações...
        // ...

        System.exit(0);
    }

}
