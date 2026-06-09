package io;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Arquivos {

    public static final Path DIR_RAIZ = FileSystems.getDefault().getPath(
        "arquivos"
    );

    public static class Caixas {

        public static String cabecalho =
            "aberto_em," +
            "fechado_em," +
            "total_pagamento," +
            "dinheiro_inicial," +
            "dinheiro_final";
        // pedidoAtual não é armazenado
        // pedidosAntigos -> arquivos/pedidos/pedidosAntigos.csv

        private static Path _dir = DIR_RAIZ.resolve("caixas");
        public static Path caixaAtual = _dir.resolve("caixaAtual.csv");
        public static Path caixasFechados = _dir.resolve("caixasFechados.csv");
    }

    public static class Contas {

        private static Path _dir = DIR_RAIZ.resolve("contas");
        public static Path admin = _dir.resolve("admin.csv");
        public static Path atendente = _dir.resolve("atendente.csv");
    }

    public static class Pedidos {

        private static Path _dir = DIR_RAIZ.resolve("pedidos");
        public static Path itemsPedido = _dir.resolve("itemsPedido.csv");
        public static Path pedidosAntigos = _dir.resolve("pedidosAntigos.csv");
    }

    public static class Produtos {

        private static Path _dir = DIR_RAIZ.resolve("produtos");
        public static Path catalogoProdutos = _dir.resolve(
            "catalogoProdutos.csv"
        );
    }

    protected static void inicializar(Path raiz) {
        raiz.toFile().mkdirs();
        Caixas._dir.toFile().mkdirs();
        Contas._dir.toFile().mkdirs();
        Pedidos._dir.toFile().mkdirs();
        Produtos._dir.toFile().mkdirs();
    }
}
