package modelos;

//meu
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

	}
