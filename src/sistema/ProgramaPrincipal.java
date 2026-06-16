package sistema;
import sistema.io.Armazenamento;
import sistema.io.Arquivos;
import sistema.modelos.Caixa;
import sistema.modelos.CatalogoProdutos;
import sistema.modelos.ItemPedido;
import sistema.modelos.Loja;
import sistema.modelos.Pedido;
import sistema.services.ContaUsuarioService;
import sistema.services.LoginService;
import sistema.interfaces.TelaFuncionarioLogin;

public class ProgramaPrincipal {

    /** Ponto de entrada do sistema e direcionamento para telas */
    public static void main(String[] args) {
        System.out.println("[SISTEMA] inicializando...");
        Armazenamento.inicializar();

        System.out.println("[SISTEMA] inicializado com sucesso.");

        // Iniciar telas abaixo
        // teste();
        if (Arquivos.Produtos.ler_produtos().length == 0) {
            Armazenamento.inserirDadosTeste();
        }

        TelaFuncionarioLogin.iniciar();
    }

    /** Exibe uma simulação do sistema, cadastrando dados de teste */
    private static void teste() {

        // LOGIN =============================================================
        // contas cadastradas: arquivos/contas/contas.csv
        String numMatricula = "1";
        String senhaLogin = "1";

        if (!LoginService.credenciaisValidas(numMatricula, senhaLogin)) {
            System.out.println("[Login] credenciais inválidas!");
            finalizarExecucao();
        }

        System.out.println("[Login] Logado com sucesso!");

        CatalogoProdutos.carregarProdutos();
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

            finalizarExecucao();
        }

        // Simulando abertura de caixa ========================================
        caixaAtual = Loja.abrirCaixa(100.0);
        System.out.printf("\n[Caixa] Caixa aberto com R$ %.2f em dinheiro\n", caixaAtual.getDinheiroInicial());

        // Gerenciar produtos =================================================
        var prod1 = CatalogoProdutos.buscar(1);

        if (prod1 != null) {
            System.out.println("Produto 1 antes da atualização: \n" + prod1.toString());

            prod1.setPrecoVenda(prod1.getPrecoVenda() + 10);
            CatalogoProdutos.atualizar(prod1.getId(), prod1); // por enquanto nao atualiza no arquivo

            prod1 = CatalogoProdutos.buscar(prod1.getId());
            System.out.println("Produto 1 depois da atualização: \n" + prod1.toString());
        } else {
            System.out.println("Produto 1 não encontrado...");
        }

        var prodRm = CatalogoProdutos.remover(2); // por enquanto não remove do arquivo

        if (prodRm != null) {
            System.out.println("Produto 2 removido:\n" + prodRm.toString());
        } else {
            System.out.println("Produto 2 não encontrado...");
        }

        // TODO: gerenciar pedido =============================================
        Pedido pedidoAtual = caixaAtual.novoPedido();
        System.out.println("[Pedido] novo pedido aberto!\n");

        pedidoAtual.atualizarItem(prod1, 3);
        ItemPedido item1 = pedidoAtual.buscarItem(prod1.getId());

        System.out.println(
            "[Pedido] item adicionado: \n\t"
            + item1.toString()
        );

        var prod3 = CatalogoProdutos.buscar(3);

        if (prod3 != null) {
            pedidoAtual.atualizarItem(prod3, 3);

            var item3 = pedidoAtual.buscarItem(prod3.getId());
            System.out.println(
                "[Pedido] item adicionado: \n\t"
                + item3.toString()
            );
        }

        System.out.printf("[Pedido] Valor total do pedido: R$ %.2f \n", pedidoAtual.getPrecoVendaTotal());

        double valorEntrada = pedidoAtual.getPrecoVendaTotal() * 1.1;
        // double valorEntrada = pedidoAtual.getPrecoVendaTotal() * 0.9;

        System.out.printf("[Pedido] Valor fornecido pelo cliente: R$ %.2f \n", valorEntrada);

        try {
            double trocoEmitido = pedidoAtual.receberPagamento("DINHEIRO", valorEntrada); // dá erro se valor de entrada for menor que o valor do pedido

            System.out.printf("[Pedido] Troco emitido: R$ %.2f\n", trocoEmitido);

            Pedido pedidoFinalizado = caixaAtual.concluirPedidoAtual();
            pedidoAtual = null;

            System.out.println(
                "[Pedido] concluído: \n" + pedidoFinalizado.toString()
            );
        } catch (Exception ex) {
            System.out.println("[Pedido] ERRO: " + ex.getMessage());

            System.out.println("[Pedido] Cancelando...");
            caixaAtual.cancelarPedidoAtual();
        }

        // Fechamento do caixa ================================================
        caixaAtual = Loja.getCaixaAtual();
        System.out.println(String.format(
            "[Caixa: Fechamento] Dinheiro em caixa: R$ %.2f\n",
            caixaAtual.getDinheiroFinal()
        ));

        Loja.fecharCaixaAtual();

        // finalizarExecucao();
    }

    /** Encerra o sistema, salvando informações e descarregando dados */
    public static void finalizarExecucao() {
        System.out.println("[SISTEMA] Finalizando execução...");

        // TODO: descarregar dados, salvar informações...
        // ...

        System.exit(0);
    }

}
