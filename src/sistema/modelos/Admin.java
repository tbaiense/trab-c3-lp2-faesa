/**
 * Representa um usuário com privilégios de Administrador/Supervisor no sistema.
 * Esta classe herda as características básicas de um {@link Funcionario} e adiciona
 * um código de autorização exclusivo, utilizado para liberar e validar operações
 * restritas ou sensíveis dentro do sistema (como gerenciamento de produtos, caixas e usuários).
 * 
 * @author Gabriel Rodrigues <gabrielrcsj@gmail.com>
 */
package sistema.modelos;


public class Admin extends Funcionario {


    private int codAutorizacao;


    public Admin(
        String numMatricula,
        String nome,
        int senhaLogin,
        int codAutorizacao
    ) {
        super(numMatricula, nome, senhaLogin);

        setCodAutorizacao(codAutorizacao);
    }

    public boolean autorizar(int codAutorizacao) {
        return this.codAutorizacao == codAutorizacao;
    }


    public int getCodAutorizacao() {
        return codAutorizacao;
    }

    public void setCodAutorizacao(int codAutorizacao) {
        this.codAutorizacao = codAutorizacao;
    }

    @Override
    public String toString() {
        return String.format(
            "numMatricula: %d | senhaLogin: %d | codAutorizacao: %d ",
            this.numMatricula, this.getSenhaLogin(), this.getCodAutorizacao()
        );
    }

	}
