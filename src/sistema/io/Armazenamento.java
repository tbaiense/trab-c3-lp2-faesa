/** Responsável por armazenar e recuperar registros usando arquivos locais.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 */

package sistema.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import sistema.io.Arquivos.Contas;
import sistema.modelos.*;

// TODO: FINALIZAR
public abstract class Armazenamento {

    // TODO: fazer usar sempre a raíz do repositório e não baseado no atual

    // public static void main(String[] args) {
        // Arquivos.inicializar();
        // teste();
    // }
    public static void inicializar() {
        Arquivos.inicializar();
    }

    // TODO: FINALIZAR (final de linha, otimizar)
    public static boolean escrever(ArquivoCSV csv) {
        boolean inserirCabecalho = false;

        if (!csv.caminho.toFile().exists()) {
            inserirCabecalho = true;
        }

        try (
            FileWriter fw = new FileWriter(csv.caminho.toFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        ) {
            if (inserirCabecalho && csv.cabecalho != null) {
                pw.println(csv.cabecalho);
            }

            // TODO: finalizar linhas com crlf (RFC 4180)
            for (var l : csv.linhas) {
                pw.println(l);
            }

            return true;
        } catch (IOException ex) {
            System.err.println(
                "Falha ao escrever arquivo CSV:\n" + csv.caminho.toString()
            );
            return false;
        }
    }

    public static boolean substituir(ArquivoCSV csv) {
        try (
            FileWriter fw = new FileWriter(csv.caminho.toFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        ) {
            pw.println(csv.cabecalho);

            // TODO: finalizar linhas com crlf (RFC 4180)
            for (var l : csv.linhas) {
                pw.println(l);
            }

            return true;
        } catch (IOException ex) {
            System.err.println(
                "Falha ao substituir conteúdo do arquivo CSV:\n" + csv.caminho.toString()
            );
            return false;
        }
    }


    public static ArquivoCSV ler(Path caminho) {
        ArquivoCSV csv = new ArquivoCSV();

        try (Scanner scanner = new Scanner(caminho)) {
            csv.caminho = caminho;
            csv.cabecalho = scanner.nextLine();

            ArrayList<String> strList = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                strList.add(scanner.nextLine());
            }

            csv.linhas = strList.toArray(new String[0]); // array só é usada pra definir o tipo de retorno
            return csv;
        } catch (IOException ex) {
            System.out.println("Falha ao carregar arquivo!");

            return null;
        }
    }

    public static void inserirDadosTeste() {
        // contas

        Funcionario[] funcionarios = Arquivos.Contas.ler_contas();

        // produtos
        Produto[] produtosTeste = {
            new Produto(1,   "Agua",   1.50, 2.50),
            new Produto(2,   "Refri",   1.50, 2.50),
            new Produto(3,   "Hamburger",   1.50, 2.50),
            new Produto(4,   "Chave de fenda",   1.50, 2.50),
            new Produto(5,   "Brita",   1.50, 2.50),
            new Produto(6,   "Cimento",   1.50, 2.50),
            new Produto(7,   "Gabinete sequíssimo",   1.50, 2.50),
            new Produto(8,   "Intel Core i5 3330 3.00 Ghz",   1.50, 2.50),
            new Produto(9,   "Jujubinha",   1.50, 2.50),
            new Produto(10,  "Pão de Queijo quentinhooo",   1.50, 2.50),
        };

        Arquivos.Produtos.inserir_produto(produtosTeste);

        var produtos = Arquivos.Produtos.ler_produtos();

        // caixa
        Loja.inicializar(Contas.ler_contas()[0]);
        Caixa c1 = Loja.abrirCaixa(200.0);

        Pedido p = c1.novoPedido();
        p.atualizarItem(produtosTeste[0], 3);
        p.receberPagamento("DINHEIRO", p.getPrecoVendaTotal() * 1.1);
        c1.concluirPedidoAtual();

        p = c1.novoPedido();
        p.atualizarItem(produtosTeste[0], 2);
        p.receberPagamento("CARTAO", p.getPrecoVendaTotal());
        c1.concluirPedidoAtual();

        p = c1.novoPedido();
        p.atualizarItem(produtosTeste[3], 1);
        p.receberPagamento("PIX", p.getPrecoVendaTotal());
        c1.concluirPedidoAtual();

        // Loja.fecharCaixaAtual();
    }
}
