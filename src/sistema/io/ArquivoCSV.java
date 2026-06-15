/**
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 */

package sistema.io;

import java.nio.file.Path;

public class ArquivoCSV {

    public String cabecalho;
    public String[] linhas;
    public Path caminho;

    public ArquivoCSV() {}

    public ArquivoCSV(String[] linhas, Path caminho) {
        this(null, linhas, caminho);
    }

    public ArquivoCSV(String cabecalho, String[] linhas, Path caminho) {
        this.cabecalho = cabecalho;
        this.linhas = linhas;
        this.caminho = caminho;
    }
}
