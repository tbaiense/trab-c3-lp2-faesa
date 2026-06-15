/** Responsável por armazenar e recuperar registros usando arquivos locais.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 */

package sistema.io;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import sistema.modelos.Admin;
import sistema.modelos.Atendente;
import sistema.modelos.Caixa;
import sistema.modelos.Funcionario;
import sistema.modelos.ItemPedido;
import sistema.modelos.Pedido;
import sistema.modelos.Produto;

public class Arquivos {

    public static final Path DIR_RAIZ = FileSystems.getDefault().getPath(
        "arquivos"
    );

    /** Inicializa os arquivos */
    protected static void inicializar() {
        Caixas.inicializar();
        Contas.inicializar();
        Pedidos.inicializar();
        Produtos.inicializar();
    }

    private static void criarArquivo(String[] cabecalho, Path caminho) {
        String[] linhas = new String[0];

        Armazenamento.escrever(
            new ArquivoCSV(cabecalhoToString(cabecalho), linhas, caminho)
        );
    }

    private static String cabecalhoToString(String[] cabecalho) {
        String strCabecalho = "";
        String separador = ",";

        for (var coluna: cabecalho) {
            strCabecalho += (coluna + separador);
        }

        strCabecalho = strCabecalho.substring(
            0, strCabecalho.length()-1
        ); // remove último separador

        return strCabecalho;
    }

    public static class Caixas {

        private static String[] cabecalho_caixaAtual = {
            "id",
            "aberto_em",
            "total_pagamento",
            "dinheiro_inicial",
            "dinheiro_final",
            "num_matricula_func_abriu"
        };

        private static String[] cabecalho_caixasFechados = {
            "id",
            "aberto_em",
            "fechado_em",
            "total_pagamento",
            "dinheiro_inicial",
            "dinheiro_final",
            "num_matricula_func_abriu"
        };
        // pedidoAtual não é armazenado
        // pedidosAntigos -> arquivos/pedidos/pedidosAntigos.csv

        private static Path _dir = DIR_RAIZ.resolve("caixas");
        private static Path caixaAtual = _dir.resolve("caixaAtual.csv");
        private static Path caixasFechados = _dir.resolve("caixasFechados.csv");

        /** Cria o arquivo CSV e inicializa-o inserindo o cabeçalho */
        protected static void inicializar() {
            _dir.toFile().mkdirs();
            criarArquivo(cabecalho_caixaAtual, caixaAtual);
            criarArquivo(cabecalho_caixasFechados, caixasFechados);
        }

        // TODO: finalizar
        /** Insere um novo registro de caixa atual. NÃO insere pedidos associados.
        *
        * Precondição: Deve ser chamado logo após abrir o caixa.
        *
        * @param c
        * @return
        */
        public static boolean inserir_caixaAtual(sistema.modelos.Caixa c) {
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
            linhasBuilder += c.getDinheiroFinal() + separador;
            linhasBuilder += c.funcionarioAbriu.numMatricula;

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                caixaAtual
            );

            Armazenamento.escrever(csv);
            return true;
        }

        // TODO: finalizar (nao le pedidosAntigos do caixa)
        public static sistema.modelos.Caixa ler_caixaAtual() {
            sistema.modelos.Caixa caixaEncontrado;
            var csv = Armazenamento.ler(caixaAtual);

            if (csv == null || csv.linhas.length == 0) {
                return null;
            }

            String[] coluna = csv.linhas[0].split(",");

            int id =                 Integer.parseInt(coluna[0]);
            LocalDateTime abertoEm = LocalDateTime.parse(coluna[1]);
            double totalPagamento  = Double.parseDouble(coluna[2]);
            double dinheiroInicial = Double.parseDouble(coluna[3]);
            double dinheiroFinal   = Double.parseDouble(coluna[4]);
            int numMatriculaFuncAbriu = Integer.parseInt(coluna[5]);

            // ler funcionário
            var contas = Arquivos.Contas.ler_contas();
            sistema.modelos.Funcionario funcAbriu = null;

            for (var c: contas) {
               if (c.numMatricula == numMatriculaFuncAbriu) {
                   funcAbriu = c;
                   break;
               }
            }

            if (funcAbriu == null) {
                throw new Error(
                    "Não foi possível recuperar o funcionário associado ao caixa atual, pois nenhum foi encontrado ao buscar pelo número de matrícula no arquivo"
                );
            }

            // TODO: ler pedidos antigos
            caixaEncontrado = new Caixa(funcAbriu, dinheiroInicial);
            caixaEncontrado.setId(id);
            caixaEncontrado.setAbertoEm(abertoEm);
            caixaEncontrado.setTotalPagamento(totalPagamento);
            caixaEncontrado.setDinheiroFinal(dinheiroFinal);

            return caixaEncontrado;
        }

        public static void remover_caixaAtual() {
            // csv com apenas o cabecalho
            var csv = new ArquivoCSV(
                cabecalhoToString(cabecalho_caixaAtual),
                new String[0],
                caixaAtual
            );

            Armazenamento.substituir(csv);
        }

        // TODO: finalizar
        /** Insere um registro de caixaFechado. NÃO insere os pedidos associados */
        public static boolean inserir_caixaFechado(sistema.modelos.Caixa c) {
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder;
            String separador = ",";

            if (c == null) {
                return false;
            }

            linhasBuilder = "";
            linhasBuilder += c.getId() + separador;
            linhasBuilder += c.getAbertoEm().toString() + separador; // TODO: obter formatado
            linhasBuilder += c.getFechadoEm().toString() + separador; // TODO: obter formatado
            linhasBuilder += c.getTotalPagamento() + separador;
            linhasBuilder += c.getDinheiroInicial() + separador;
            linhasBuilder += c.getDinheiroFinal() + separador;
            linhasBuilder += c.funcionarioAbriu.numMatricula;


            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                caixasFechados
            );

            Armazenamento.escrever(csv);
            return true;
        }

        public static sistema.modelos.Caixa[] ler_caixasFechados() {
            sistema.modelos.Caixa caixa;
            var caixasList = new ArrayList<sistema.modelos.Caixa>();
            var csv = Armazenamento.ler(caixasFechados);
            String[] coluna;

            int id;
            LocalDateTime abertoEm, fechadoEm;
            double totalPagamento;
            double dinheiroInicial;
            double dinheiroFinal;
            int numMatriculaFuncAbriu;
            sistema.modelos.Funcionario funcAbriu;

            for (var linha: csv.linhas) {
                coluna = csv.linhas[0].split(",");

                id =                 Integer.parseInt(coluna[0]);
                abertoEm = LocalDateTime.parse(coluna[1]);
                fechadoEm = LocalDateTime.parse(coluna[2]);
                totalPagamento  = Double.parseDouble(coluna[3]);
                dinheiroInicial = Double.parseDouble(coluna[4]);
                dinheiroFinal   = Double.parseDouble(coluna[5]);
                numMatriculaFuncAbriu = Integer.parseInt(coluna[6]);

                // ler funcionário
                funcAbriu = null;

                for (var c: Arquivos.Contas.ler_contas()) {
                   if (c.numMatricula == numMatriculaFuncAbriu) {
                       funcAbriu = c;
                       break;
                   }
                }

                if (funcAbriu == null) {
                    throw new Error(
                        "Não foi possível recuperar o funcionário associado ao caixa atual, pois nenhum foi encontrado ao buscar pelo número de matrícula no arquivo"
                    );
                }

                // TODO: ler pedidos antigos
                caixa = new Caixa(funcAbriu, dinheiroInicial);
                caixa.setId(id);
                caixa.setAbertoEm(abertoEm);
                caixa.setFechadoEm(fechadoEm);
                caixa.setTotalPagamento(totalPagamento);
                caixa.setDinheiroFinal(dinheiroFinal);

                caixasList.add(caixa);
            }
            return caixasList.toArray(new sistema.modelos.Caixa[0]);
        }
    }

    public static class Contas {

        private static String[] cabecalho_contas = {
            "numMatricula",
            "cargo", // define se é admin ou nao
            "nome",
            "senhaLogin",
            "codAutorizacao"
        };

        private static Path _dir = DIR_RAIZ.resolve("contas");
        private static Path contas = _dir.resolve("contas.csv");

        /** Cria o arquivo CSV e inicializa-o inserindo o cabeçalho */
        protected static void inicializar() {
            _dir.toFile().mkdirs();
            if (!contas.toFile().exists()) {
                criarArquivo(cabecalho_contas, contas);

                var admin = new Admin("1", "Ademiro", 1, 123);
                inserir_conta(admin);
                inserir_conta(new Atendente("2", "Atendente José", 2));

                System.out.println("\n[SISTEMA] Credenciais de admin:\n--> " + admin + "\n");
            }
        }

        // TODO: finalizar
        public static boolean inserir_conta(sistema.modelos.Funcionario f) {
            // salvar caixa
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder = "";
            String separador = ",";

            if (f == null) {
                return false;
            }

            String cargo = "atendente";
            String codAutorizacao = "";
            if (f instanceof sistema.modelos.Admin) {
                cargo = "admin";
                codAutorizacao = Integer.toString(((Admin)f).getCodAutorizacao());
            }

            linhasBuilder += f.numMatricula + separador;
            linhasBuilder += cargo + separador;
            linhasBuilder += f.nome + separador;
            linhasBuilder += f.getSenhaLogin() + separador;
            linhasBuilder += codAutorizacao;

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                contas
            );

            Armazenamento.escrever(csv);
            return true;
        }

        // TODO: implementar
        public static boolean remover_conta(int id) {
            return false;
        }

        // TODO: implementar
        public static boolean atualizar_conta(int id, sistema.modelos.Funcionario f) {
            return false;
        }

        public static sistema.modelos.Funcionario[] ler_contas() {
            sistema.modelos.Funcionario f;
            ArrayList<sistema.modelos.Funcionario> lista = new ArrayList<sistema.modelos.Funcionario>();
            String [] coluna;

            ArquivoCSV csv = Armazenamento.ler(contas);

            String numMatricula;
            String cargo;
            String nome;
            int senhaLogin;
            int codAutorizacao;

            for (String l: csv.linhas) {
               coluna = l.split(",");

                numMatricula = coluna[0];
                cargo =        coluna[1];
                nome =         coluna[2];
                senhaLogin = Integer.parseInt(coluna[3]);

               if (cargo.equals("admin")) { // cargo
                   codAutorizacao = Integer.parseInt(coluna[4]);

                   f = new sistema.modelos.Admin(
                        numMatricula,
                        nome,
                        senhaLogin,
                        codAutorizacao
                   );
               } else {
                   f = new Atendente(numMatricula, nome, senhaLogin);
               }

               lista.add(f);
            }

            return lista.toArray(new sistema.modelos.Funcionario[0]);
        }

    }

    public static class Pedidos {

        private static String[] cabecalho_pedidosAntigos = {
            "id_caixa",
            "id",
            "finalizado_em",
            "forma_pagamento",
            "taxa_cartao",
            "preco_venda_total",
            "lucro_total"
        };

        private static String[] cabecalho_itensPedido = {
            "id_pedido",
            "id_produto",
            "quantidade"
        };

        private static Path _dir = DIR_RAIZ.resolve("pedidos");
        private static Path itensPedido = _dir.resolve("itensPedido.csv");
        private static Path pedidosAntigos = _dir.resolve("pedidosAntigos.csv");

        /** Cria o arquivo CSV e inicializa-o inserindo o cabeçalho */
        public static void inicializar() {
            _dir.toFile().mkdirs();

            criarArquivo(cabecalho_pedidosAntigos, pedidosAntigos);
            criarArquivo(cabecalho_itensPedido, itensPedido);
        }

        /** Insere um novo registro de pedido antigo nos arquivos, associando-o ao caixa informado.
         * NÃO insere os itens associados.
         * @param pedido - O pedido finalizado
         * @param caixaAssociado - O caixa onde o pedido foi finalizado
         * @return true, se a operação foi bem sucedida
         */
        public static boolean inserir_pedidoAntigo(
            sistema.modelos.Pedido pedido,
            sistema.modelos.Caixa caixaAssociado
        ) {
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder = "";
            String separador = ",";

            if (pedido == null || !pedido.isFinalizado()) {
                return false;
            }

            linhasBuilder += caixaAssociado.getId() + separador;
            linhasBuilder += pedido.getId() + separador;
            linhasBuilder += pedido.getFinalizadoEm() + separador;
            linhasBuilder += pedido.getFormaPagamento() + separador;
            linhasBuilder += pedido.getTaxaCartao() + separador;
            linhasBuilder += pedido.getPrecoVendaTotal() + separador;
            linhasBuilder += pedido.getLucro();

            strList.add(linhasBuilder);

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                pedidosAntigos
            );

            Armazenamento.escrever(csv);
            return true;
        }


        // TODO: implementar
        public static sistema.modelos.Pedido[] ler_pedidos() {
            int qtdLinhas = Armazenamento.ler(pedidosAntigos).linhas.length;
            return new Pedido[qtdLinhas];
        }

        public static boolean inserir_itensPedido (Pedido pedido, sistema.modelos.ItemPedido... itens) {
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder;
            String separador = ",";

            if (pedido == null || !pedido.isFinalizado()
                || itens == null || itens.length == 0
            ) {
                return false;
            }

            for (var item: itens) {
                linhasBuilder = "";
                linhasBuilder += pedido.getId() + separador;
                linhasBuilder += item.getProduto().getId() + separador;
                linhasBuilder += item.getQuantidade();

                strList.add(linhasBuilder);
            }

            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                itensPedido
            );

            Armazenamento.escrever(csv);
            return true;
        }

        // TODO: implementar
        public static sistema.modelos.ItemPedido[] ler_itensPedido(int idPedido) {
            int qtdLinhas = Armazenamento.ler(itensPedido).linhas.length;
            return new ItemPedido[qtdLinhas];
        }
    }

    public static class Produtos {
        private static String[] cabecalho_catalogoProdutos = {
            "id",
            "nome",
            "precoCusto",
            "precoVenda"
        };

        private static Path _dir = DIR_RAIZ.resolve("produtos");
        private static Path catalogoProdutos = _dir.resolve(
            "catalogoProdutos.csv"
        );

        /** Cria o arquivo CSV e inicializa-o inserindo o cabeçalho */
        public static void inicializar() {
            _dir.toFile().mkdirs();
            criarArquivo(cabecalho_catalogoProdutos, catalogoProdutos);
        }

        // TODO: finalizar
        public static boolean inserir_produto(sistema.modelos.Produto... pList) {
            // salvar caixa
            ArrayList<String> strList = new ArrayList<String>();
            String linhasBuilder;
            String separador = ",";

            if (pList == null) {
                return false;
            }

            for (var p: pList) {
                linhasBuilder = "";
                linhasBuilder += p.getId() + separador;
                linhasBuilder += p.getNome() + separador;
                linhasBuilder += p.getPrecoCusto() + separador;
                linhasBuilder += p.getPrecoVenda();

                strList.add(linhasBuilder);
            }


            ArquivoCSV csv = new ArquivoCSV(
                strList.toArray(new String[0]),
                catalogoProdutos
            );

            Armazenamento.escrever(csv);
            return true;
        }

        public static sistema.modelos.Produto[] ler_produtos() {
            sistema.modelos.Produto p;
            var lista = new ArrayList<sistema.modelos.Produto>();
            String [] coluna;

            ArquivoCSV csv = Armazenamento.ler(catalogoProdutos);

            int id;
            String nome;
            double precoCusto;
            double precoVenda;

            for (String l: csv.linhas) {
                coluna = l.split(",");

                id =         Integer.parseInt(coluna[0]);
                nome =       coluna[1];
                precoCusto = Double.parseDouble(coluna[2]);
                precoVenda = Double.parseDouble(coluna[3]);

                p = new Produto(id, nome, precoCusto, precoVenda);
                lista.add(p);
            }

            return lista.toArray(new sistema.modelos.Produto[0]);
        }

        // TODO: implementar
        public static boolean remover_produto(int id) {
           return false;
        }

        // TODO: implementar
        public static boolean atualizar_produto(
            int id, sistema.modelos.Produto produto
        ) {
           return false;
        }
    }
}
