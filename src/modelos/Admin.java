package modelos;

//meu
public class Admin extends Funcionario {
    
   
    private int codAutorizacao;

    
    public Admin() {
        super();
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

