package modelos;

import java.time.LocalDateTime; //biblioteca para pegar data e hora.
import java.time.format.DateTimeFormatter; //biblioteca para formatar data e hora.
import java.util.ArrayList;

public class Caixa {
	private LocalDateTime abertoEm = null;
	private LocalDateTime fechadoEm = null;
	private double totalPagamento = 0;
	private double dinheiroInicial = 0;
	private double dinheiroFinal = 0;
	private Pedido pedidoAtual;
	private ArrayList<Pedido> pedidosAntigos = new ArrayList<Pedido>();
	
	DateTimeFormatter dthrFormatadaBrasil = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //Formata a hora para formato brasileiro.

	public Caixa() {

	}

	public LocalDateTime getAbertoEm() {
		abertoEm = LocalDateTime.now();
		return abertoEm;
	}

	/*public void setAbertoEm(LocalDateTime abertoEm) {
		this.abertoEm = abertoEm;
	}*/

	public LocalDateTime getFechadoEm() {
		fechadoEm = LocalDateTime.now();
		return fechadoEm;
	}

	/*public void setFechadoEm(LocalDateTime fechadoEm) {
		this.fechadoEm = fechadoEm;
	}*/

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

	public double getDinheiroFinal() {
		return dinheiroFinal;
	}
	
	
}
