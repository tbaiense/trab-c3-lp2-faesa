package io;

import io.ArquivoCSV;
import io.Arquivos;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import modelos.*;

// TODO: FINALIZAR
public abstract class Armazenamento {

    // TODO: fazer usar sempre a raíz do repositório e não baseado no atual

    public static void main(String[] args) {
        inicializar();
        teste();
    }

    public static void inicializar() {
        // teste();`
        Arquivos.inicializar(Arquivos.DIR_RAIZ);

        String cabecalho = "foo,bar,baz";
        String[] linhas = new String[0];

        // TODO: alterar para escrever cabecalho correto
        escrever(new ArquivoCSV(cabecalho, linhas, Arquivos.Caixas.caixaAtual));
        escrever(
            new ArquivoCSV(cabecalho, linhas, Arquivos.Caixas.caixasFechados)
        );
        escrever(new ArquivoCSV(cabecalho, linhas, Arquivos.Contas.admin));
        escrever(new ArquivoCSV(cabecalho, linhas, Arquivos.Contas.atendente));
        escrever(
            new ArquivoCSV(cabecalho, linhas, Arquivos.Pedidos.itemsPedido)
        );
        escrever(
            new ArquivoCSV(cabecalho, linhas, Arquivos.Pedidos.pedidosAntigos)
        );
        escrever(
            new ArquivoCSV(
                cabecalho,
                linhas,
                Arquivos.Produtos.catalogoProdutos
            )
        );
    }

    // TODO: FINALIZAR (final de linha, otimizar)
    public static boolean escrever(ArquivoCSV csv) {
        try (PrintWriter pw = new PrintWriter(csv.caminho.toFile())) {
            pw.println(csv.cabecalho);

            // TODO: otimizar usando buffered reader
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

    // TODO: finalizar
    public static boolean salvarCaixasFechados(Caixa[] caixas) {
        // salvar caixa
        ArrayList<String> strList = new ArrayList<String>();
        String linhasBuilder;
        String separador = ",";

        for (var c : caixas) {
            if (c == null) {
                continue;
            }

            linhasBuilder = "";
            linhasBuilder += c.getAbertoEm().toString() + separador; // TODO: obter formatado
            linhasBuilder += c.getFechadoEm().toString() + separador; // Todo: obter formatado
            linhasBuilder += c.getTotalPagamento() + separador;
            linhasBuilder += c.getDinheiroInicial() + separador;
            linhasBuilder += c.getDinheiroFinal();

            // TODO: adiocioar id do funcionario

            strList.add(linhasBuilder);
        }

        ArquivoCSV csv = new ArquivoCSV(
            Arquivos.Caixas.cabecalho,
            strList.toArray(new String[0]),
            Arquivos.Caixas.caixasFechados
        );

        escrever(csv);
        // salvar pedidos antigos
        return false;
    }

    private static void teste() {
        Caixa[] caixas = new Caixa[3];

        Caixa c1 = new Caixa(new Funcionario(), 0.0);
        c1.setFechadoEm(LocalDateTime.now());
        c1.setAbertoEm(LocalDateTime.now());

        caixas[0] = c1;
        salvarCaixasFechados(caixas);
    }
}
