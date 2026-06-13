package sistema.modelos;

import java.time.LocalDateTime; //biblioteca para pegar data e hora.
import java.util.ArrayList;
import java.util.Objects;

public class Caixa {

	private int id; // usado para associá-lo os pedidos antigos nos arquivos csv
	public Funcionario funcionarioAbriu = null;
	private Pedido pedidoAtual = null;
	private LocalDateTime abertoEm = null;
	private LocalDateTime fechadoEm = null;
	private double totalPagamento = 0;
	private double dinheiroInicial = 0;
	private double dinheiroFinal = 0;
	private ArrayList<Pedido> pedidosAntigos = new ArrayList<Pedido>();

	public Caixa() {
		this.funcionarioAbriu = null;
		this.dinheiroInicial = 0.0;
		this.dinheiroFinal = 0.0;
	}

	public Caixa(Funcionario funcAbriu, double dinheiroInicial) {
		this.funcionarioAbriu = funcAbriu;
		this.dinheiroInicial = dinheiroInicial;
		this.dinheiroFinal = dinheiroInicial;
	}

    public int getId() {
        return this.id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    public boolean possuiPedidoAtual() {
        return pedidoAtual != null;
    }

    protected void setPedidoAtual(Pedido pedidoAtual) {
        this.pedidoAtual = pedidoAtual;
    }

    public Pedido novoPedido() {
		//todo: Implementar validação, obter ID do pedido
		pedidoAtual = new Pedido();
		return pedidoAtual;
	}

	public Pedido buscarPedido(int id) {
		//obter parâmetro ID do pedido
		Pedido pedidoBuscado = null;
		for (int i = 0; i < pedidosAntigos.size(); i++) {
			//Colocar verificação de id para finalizar for
			pedidoBuscado = pedidosAntigos.get(i);
			//Retorna o pedido que for achado
			return pedidoBuscado;
		}
		//Retorna um pedido nulo
		return pedidoBuscado;
	}

	public boolean concluirPedidoAtual() {
		//todo: implementar validação e troca de estado
		if(pedidoAtual == null) {
			return false;
		}
		pedidosAntigos.add(pedidoAtual);
		pedidoAtual = null;
		return true;
	}

	public boolean cancelarPedidoAtual() {
		//todo: implementar validação e troca de estado
		if(pedidoAtual == null) {
			return false;
		}
		pedidosAntigos.add(pedidoAtual);
		pedidoAtual = null;
		return true;
	}


    public LocalDateTime getAbertoEm() {
        return abertoEm;
    }

    protected void setAbertoEm(LocalDateTime abertoEm) {
        this.abertoEm = abertoEm;
    }

    public LocalDateTime getFechadoEm() {
        return fechadoEm;
    }

    protected void setFechadoEm(LocalDateTime fechadoEm) {
        this.fechadoEm = fechadoEm;
    }

    public double getTotalPagamento() {
        return totalPagamento;
    }

    public void setTotalPagamento(double totalPagamento) {
        this.totalPagamento = totalPagamento;
    }

	public double getDinheiroInicial() {
        return dinheiroInicial;
    }

    public void setDinheiroInicial(double dinheiroInicial) {
        this.dinheiroInicial = dinheiroInicial;
    }

    public void setDinheiroFinal(double dinheiroFinal) {
        this.dinheiroFinal = dinheiroFinal;
    }

    public double getDinheiroFinal() {
        return dinheiroFinal;
    }

	@Override
	public String toString() {
		String resposta = "";
		for (int i = 0; i < pedidosAntigos.size(); i++) {
			resposta+="Caixa aberto em: " + abertoEm + ", fechado em: " + fechadoEm + ", pagamento total: " + totalPagamento+ ", dinheiro inicial: " + dinheiroInicial + ", dinheiro final: "+dinheiroFinal;
		}
		return resposta;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caixa other = (Caixa) obj;
		return Objects.equals(abertoEm, other.abertoEm)
				&& Double.doubleToLongBits(dinheiroFinal) == Double.doubleToLongBits(other.dinheiroFinal)
				&& Double.doubleToLongBits(dinheiroInicial) == Double.doubleToLongBits(other.dinheiroInicial)
				&& Objects.equals(fechadoEm, other.fechadoEm)
				&& Objects.equals(funcionarioAbriu, other.funcionarioAbriu)
				&& Objects.equals(pedidoAtual, other.pedidoAtual)
				&& Objects.equals(pedidosAntigos, other.pedidosAntigos)
				&& Double.doubleToLongBits(totalPagamento) == Double.doubleToLongBits(other.totalPagamento);
	}

}
