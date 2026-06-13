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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import sistema.modelos.*;

// TODO: FINALIZAR
public abstract class Armazenamento {

    // TODO: fazer usar sempre a raíz do repositório e não baseado no atual

    // public static void main(String[] args) {
        // Arquivos.inicializar();
        // teste();
    // }

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

    private static void teste() {
        // contas
        Funcionario joao = new Atendente("1", "joao", 1);
        Arquivos.Contas.inserir_conta(joao);
        Funcionario[] funcionarios = Arquivos.Contas.ler_contas();

        System.out.println("Contas cadastradas lidas: \n" + Arrays.toString(funcionarios));

        // caixa
        Loja.inicializar(joao);
        Caixa c1 = Loja.abrirCaixa(200.0);
        c1.setId(1);

        c1.setFechadoEm(LocalDateTime.now());
        c1.setAbertoEm(LocalDateTime.now());

        Arquivos.Caixas.inserir_caixaAtual(c1);

        Pedido p = c1.novoPedido();
        c1.concluirPedidoAtual();

        Arquivos.Pedidos.inserir_pedidoAntigo(p, c1);

        p = c1.novoPedido();
        c1.concluirPedidoAtual();

        Arquivos.Pedidos.inserir_pedidoAntigo(p, c1);

        p = c1.novoPedido();
        c1.concluirPedidoAtual();

        Arquivos.Pedidos.inserir_pedidoAntigo(p, c1);

        Loja.fecharCaixaAtual();

        c1.setTotalPagamento(1000.0);
        c1.setDinheiroFinal(800.0);

        Arquivos.Caixas.inserir_caixaFechado(c1);
    }
}
