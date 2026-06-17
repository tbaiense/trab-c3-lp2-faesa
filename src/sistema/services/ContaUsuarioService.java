/** Permite realizar buscas e gerencias as contas cadastradas.
 *
 * @author Thiago M. Baiense <thiagomourabaiense@gmail.com>
 *
 */

package sistema.services;

import sistema.io.Arquivos;
import sistema.modelos.Atendente;
import sistema.modelos.Funcionario;

public class ContaUsuarioService {

    @Override
	public String toString() {
		return String.format(
			"[ContaUsuarioService] Contas carregadas: %d\n",
			contas == null 
			? "nenhuma" 
			: contas.length
		);
	}

	private static Funcionario[] contas = null;

    /** Lê as contas dos arquivos e armazena internamente na classe
     *
     */
    public static void atualizarContas() {
        try {
            contas = Arquivos.Contas.ler_contas();
        } catch (Exception ex) {
            System.err.println(
                "[services.ContaUsuarioService.atualizarContas(...)] \n"
                + "\t-> [ERRO] "
                + "Falha ao ler contas do arquivo CSV: \n\t\t"
                + ex.getLocalizedMessage());
        }
    }

    /** Retorna as contas carregadas. Ao criar uma nova conta, será necessário chamar o método {@code carregarContas()} para atualizar os resultados.
    *
    * @return as contas carregadas.
 */
    public static Funcionario[] obterTodos() {
        if (contas == null) {
            atualizarContas();
        }

        return contas;
    }

    /** Remove as contas carregadas do cache da classe pra liberar memória. */
    public static void descarregarContas() {
        contas = null;
    }

    /** Cadastra um novo atendente gerando um numMatricula e senhaLogin sequencial */
    public static Atendente cadastrarAtendente(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException(
                "Nome do atendente a ser cadastrado não pode ser nulo"
            );
        }

        int numMatricula = Arquivos.Contas.ler_contas().length;
        int senhaLogin = numMatricula;

        var atendente = new Atendente(String.valueOf(numMatricula), nome, senhaLogin);
        Arquivos.Contas.inserir_conta(atendente);

        return atendente;
    }
}
