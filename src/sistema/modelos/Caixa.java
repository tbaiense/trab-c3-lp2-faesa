package sistema.modelos;

import java.time.LocalDateTime; //biblioteca para pegar data e hora.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import sistema.io.Arquivos;

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


	public Caixa(Funcionario funcAbriu, double dinheiroInicial, Pedido[] pedidosAntigos) {
    	this(funcAbriu, dinheiroInicial);
        this.pedidosAntigos.addAll(Arrays.asList(pedidosAntigos));
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

    public Pedido[] getPedidosAntigos() {
        if (pedidosAntigos == null) {
            return null;
        }

        return pedidosAntigos.toArray(new Pedido[0]);
    }

    public boolean possuiPedidoAtual() {
        return pedidoAtual != null;
    }

    protected void setPedidoAtual(Pedido pedidoAtual) {
        this.pedidoAtual = pedidoAtual;
        System.out.println("Pedido atual setado para " + pedidoAtual);
    }

    public Pedido novoPedido() {
        if (possuiPedidoAtual()) {
            throw new Error(
                "Não é possível abrir um novo pedido, pois já existe um aberto"
            );
        }

		setPedidoAtual(new Pedido());
		return getPedidoAtual();
	}

	public Pedido buscarPedidoAntigo(int id) {
	    for (var p: getPedidosAntigos()) {
			if (p.getId() == id) {
    			return p;
			}
		}

		return null;
	}

	/** Conclui o pedido e armazena nos arquivos */
	public void concluirPedidoAtual() {
		if (!possuiPedidoAtual()) {
		    throw new Error(
    			"não é possível concluir o pedido atual, pois não há um pedido atual associado à loja"
			);
		}

		if (pedidoAtual.getItens().isEmpty()) {
            throw new Error(
                "Não é possível concluir o pedido, pois não há itens cadastrados"
            );
		}

		pedidoAtual.setEstado(Pedido.Estado.CONCLUIDO);
		pedidoAtual.setFinalizadoEm(LocalDateTime.now());

		pedidosAntigos.add(pedidoAtual);
		setTotalPagamento(pedidoAtual.getPrecoVendaTotal()); // TODO: talvez mudar a forma de calcular o preco total (colocar a taxa de cartão para o cliente pagar? assim mantemos somente um valor final de pagamento)

		Arquivos.Pedidos.inserir_pedidoAntigo(pedidoAtual, this);
		setPedidoAtual(null);
	}

	/** Cancela o pedido e armazena nos arquivos */
	public void cancelarPedidoAtual() {
		if(!possuiPedidoAtual()) {
            throw new Error(
    			"não é possível concluir o pedido atual, pois não há um pedido atual associado à loja"
            );
		}

		pedidoAtual.setEstado(Pedido.Estado.CANCELADO);
		pedidoAtual.setFinalizadoEm(LocalDateTime.now());

		pedidosAntigos.add(pedidoAtual);
		setPedidoAtual(null);

		Arquivos.Pedidos.inserir_pedidoAntigo(pedidoAtual, this);
	}


    public LocalDateTime getAbertoEm() {
        return abertoEm;
    }

    public void setAbertoEm(LocalDateTime abertoEm) {
        this.abertoEm = abertoEm;
    }

    public LocalDateTime getFechadoEm() {
        return fechadoEm;
    }

    public void setFechadoEm(LocalDateTime fechadoEm) {
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
