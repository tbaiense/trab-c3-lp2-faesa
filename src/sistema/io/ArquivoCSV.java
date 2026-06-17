/**
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 */

package sistema.io;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class ArquivoCSV {

    @Override
	public String toString() {
		return "[io: ArquivoCSV]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoCSV other = (ArquivoCSV) obj;
		return Objects.equals(cabecalho, other.cabecalho) && Objects.equals(caminho, other.caminho)
				&& Arrays.equals(linhas, other.linhas);
	}

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
