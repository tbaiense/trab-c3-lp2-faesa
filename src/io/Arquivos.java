package io;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import modelos.Pedido;

public class Arquivos {

    public static final Path DIR_RAIZ = FileSystems.getDefault().getPath(
        "arquivos"
    );

    public static void inicializar() {
        Caixas.inicializar();
        Contas.inicializar();
        Pedidos.inicializar();
        Produtos.inicializar();
    }

    public static class Caixas {

        public static String cabecalho_caixaAtual =
            "id," +
            "aberto_em," +
            "total_pagamento," +
            "dinheiro_inicial," +
            "dinheiro_final";
        // + "funcionario_num_matricula";

        public static String cabecalho_caixasFechados =
            "id," +
            "aberto_em," +
            "fechado_em," +
            "total_pagamento," +
            "dinheiro_inicial," +
            "dinheiro_final";
        // + "funcionario_num_matricula";
        // pedidoAtual não é armazenado
        // pedidosAntigos -> arquivos/pedidos/pedidosAntigos.csv

        private static Path _dir = DIR_RAIZ.resolve("caixas");
        public static Path caixaAtual = _dir.resolve("caixaAtual.csv");
        public static Path caixasFechados = _dir.resolve("caixasFechados.csv");

        public static void inicializar() {
            String[] linhas = new String[0];

            _dir.toFile().mkdirs();
            Armazenamento.escrever(
                new ArquivoCSV(cabecalho_caixaAtual, linhas, caixaAtual)
            );
            Armazenamento.escrever(
                new ArquivoCSV(cabecalho_caixasFechados, linhas, caixasFechados)
            );
        }

        // TODO: finalizar
        public static boolean inserir_caixaAtual(modelos.Caixa c) {
            // salvar caixa
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder = "";
            String separador = ",";

            if (c == null) {
                return false;
            }

            linhasBuilder += c.getId() + separador;
            linhasBuilder += c.getAbertoEm().toString() + separador; // TODO: obter formatado
            linhasBuilder += c.getTotalPagamento() + separador;
            linhasBuilder += c.getDinheiroInicial() + separador;
            linhasBuilder += c.getDinheiroFinal();

            // TODO: descomentar assim que implementarem `funcionario.numMatricula`
            // linhasBuilder += c.funcionarioAbriu.numMatricula;

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                caixaAtual
            );

            Armazenamento.escrever(csv);
            // salvar pedidos antigos
            return false;
        }

        // TODO: finalizar
        public static boolean inserir_caixaFechado(modelos.Caixa c) {
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder;
            String separador = ",";

            if (c == null) {
                return false;
            }

            linhasBuilder = "";
            linhasBuilder += c.getId() + separador;
            linhasBuilder += c.getAbertoEm().toString() + separador; // TODO: obter formatado
            linhasBuilder += c.getFechadoEm().toString() + separador; // Todo: obter formatado
            linhasBuilder += c.getTotalPagamento() + separador;
            linhasBuilder += c.getDinheiroInicial() + separador;
            linhasBuilder += c.getDinheiroFinal();

            // TODO: adiocioar id do funcionario

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                caixasFechados
            );

            Armazenamento.escrever(csv);
            return false;
        }
    }

    public static class Contas {

        private static Path _dir = DIR_RAIZ.resolve("contas");
        public static Path admin = _dir.resolve("admin.csv");
        public static Path atendente = _dir.resolve("atendente.csv");

        public static void inicializar() {
            String[] linhas = new String[0];
            String cabecalho = "todo, todo, todo";
            _dir.toFile().mkdirs();

            Armazenamento.escrever(new ArquivoCSV(cabecalho, linhas, admin));
            Armazenamento.escrever(
                new ArquivoCSV(cabecalho, linhas, atendente)
            );
        }
    }

    public static class Pedidos {

        public static String cabecalho_pedidosAntigos =
            "id_caixa, " +
            "id, " +
            "finalizado_em, " +
            "forma_pagamento, " +
            "taxa_cartao, " +
            "preco_venda_total";

        private static Path _dir = DIR_RAIZ.resolve("pedidos");
        public static Path itemsPedido = _dir.resolve("itemsPedido.csv");
        public static Path pedidosAntigos = _dir.resolve("pedidosAntigos.csv");

        public static void inicializar() {
            String[] linhas = new String[0];
            String cabecalho = "todo, todo, todo";
            _dir.toFile().mkdirs();

            Armazenamento.escrever(
                new ArquivoCSV(cabecalho, linhas, itemsPedido)
            );
            Armazenamento.escrever(
                new ArquivoCSV(cabecalho, linhas, pedidosAntigos)
            );
        }

        /** Insere um novo registro de pedido antigo nos arquivos, associando-o ao caixa informado.
         *
         * @param pedido - O pedido finalizado
         * @param caixaAssociado - O caixa onde o pedido foi finalizado
         * @return true, se a operação foi bem sucedida
         */
        public static boolean inserir_pedidoAntigo(
            modelos.Pedido pedido,
            modelos.Caixa caixaAssociado
        ) {
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder = "";
            String separador = ",";

            if (pedido == null) {
                return false;
            }

            linhasBuilder += caixaAssociado.getId() + separador;

            // TODO: descomentar quando implementarem getters de Pedido
            // linhasBuilder += pedido.getId() + separador;
            // linhasBuilder += pedido.getFinalizadoEm() + separador;
            // linhasBuilder += pedido.getFormaPagamento() + separador;
            // linhasBuilder += pedido.getTaxaCartao() + separador;
            // linhasBuilder += pedido.getPrecoVendaTotal();

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                pedidosAntigos
            );

            Armazenamento.escrever(csv);
            return false;
        }
    }

    public static class Produtos {

        private static Path _dir = DIR_RAIZ.resolve("produtos");
        public static Path catalogoProdutos = _dir.resolve(
            "catalogoProdutos.csv"
        );

        public static void inicializar() {
            String[] linhas = new String[0];
            String cabecalho = "todo, todo, todo";
            _dir.toFile().mkdirs();

            Armazenamento.escrever(
                new ArquivoCSV(cabecalho, linhas, catalogoProdutos)
            );
        }
    }
}
