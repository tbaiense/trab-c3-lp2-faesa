/**
 * Classe de modelo que representa a entidade Caixa no sistema.
 * Responsável por gerenciar o estado financeiro de uma sessão de vendas, controlar o fluxo
 * de pedidos ativos, armazenar o histórico de pedidos concluídos/cancelados e interagir
 * com a camada de persistência de arquivos CSV.
 * @author Igor Rios Simões <riossigor@gmail.com>
 */

package sistema.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import sistema.io.Arquivos;

public class Caixa {

	private int id; // usado para associá-lo os pedidos antigos nos arquivos csv
	public Funcionario funcionarioAbriu = null;
	private Pedido pedidoAtual = null;

	// Controle temporal da sessão
	private LocalDateTime abertoEm = null;
	private LocalDateTime fechadoEm = null;

	// Atributos de balanço financeiro e auditoria monetária
	private double totalPagamento = 0;
	private double dinheiroInicial = 0;
	private double dinheiroFinal = 0;

	// Coleção interna contendo o histórico de vendas processadas nesta sessão
	private ArrayList<Pedido> pedidosAntigos = new ArrayList<Pedido>();

	/**
	 * Construtor padrão (vazio). Inicializa o caixa com valores zerados.
	 */
	public Caixa() {
		this.funcionarioAbriu = null;
		this.dinheiroInicial = 0.0;
		this.dinheiroFinal = 0.0;
	}

	/**
	 * Construtor sobrecarregado para abertura de caixa em tempo real.
	 * @param funcAbriu O funcionário autenticado que realizou a abertura.
	 * @param dinheiroInicial Fundo de reserva (troco) inserido na abertura.
	 */
	public Caixa(Funcionario funcAbriu, double dinheiroInicial) {
		this.funcionarioAbriu = funcAbriu;
		this.dinheiroInicial = dinheiroInicial;
		this.dinheiroFinal = dinheiroInicial; // O saldo final inicia igual ao fundo de reserva
	}

	/**
	 * Construtor completo utilizado principalmente pela camada de I/O para reconstruir
	 * caixas antigos salvos no arquivo CSV com seus respectivos históricos de pedidos.
	 */
	public Caixa(Funcionario funcAbriu, double dinheiroInicial, Pedido[] pedidosAntigos) {
		this(funcAbriu, dinheiroInicial);
		this.pedidosAntigos.addAll(Arrays.asList(pedidosAntigos));
	}

	// Métodos Getters e Setters com restrição de visibilidade (protected onde aplicável)

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pedido getPedidoAtual() {
		return pedidoAtual;
	}

	/**
	 * Converte a lista interna de pedidos antigos para um Array nativo de Pedidos.
	 * @return Array contendo o histórico de vendas da sessão.
	 */
	public Pedido[] getPedidosAntigos() {
		if (pedidosAntigos == null) {
			return null;
		}
		return pedidosAntigos.toArray(new Pedido[0]);
	}

	/**
	 * Verifica se há uma transação ativa em andamento no caixa.
	 */
	public boolean possuiPedidoAtual() {
		return pedidoAtual != null;
	}

	protected void setPedidoAtual(Pedido pedidoAtual) {
		this.pedidoAtual = pedidoAtual;
	}

	/**
	 * Inicia uma nova venda, bloqueando a operação caso uma transação anterior não tenha sido fechada.
	 * @return A instância do novo pedido gerado.
	 */
	public Pedido novoPedido() {
		if (possuiPedidoAtual()) {
			throw new Error(
				"Não é possível abrir um novo pedido, pois já existe um aberto"
			);
		}
		setPedidoAtual(new Pedido());
		return getPedidoAtual();
	}

	/**
	 * Varre o histórico de pedidos finalizados deste caixa para encontrar um registro específico.
	 * @param id Identificador único do pedido procurado.
	 * @return O objeto Pedido se encontrado, ou null caso contrário.
	 */
	public Pedido buscarPedidoAntigo(int id) {
		for (var p: getPedidosAntigos()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}


	/** Conclui o pedido, define um id, retorna a referência para o pedido finalizado e armazena nos arquivos */
	public Pedido concluirPedidoAtual() {
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

		if (pedidoAtual.getFormaPagamento() == null) {
		    throw new Error(
    			"É necessário definir a forma de pagamento antes de concluir o pedido"
			);
		}

		var finalizado = pedidoAtual; // copia a referência

        // Atualiza o estado interno do pedido e define a estampa de tempo
		setPedidoAtual(null);

		finalizado.setEstado(Pedido.Estado.CONCLUIDO);
		finalizado.setFinalizadoEm(LocalDateTime.now());

		pedidosAntigos.add(finalizado);
		setTotalPagamento(finalizado.getPrecoVendaTotal()); // TODO: talvez mudar a forma de calcular o preco total (colocar a taxa de cartão para o cliente pagar? assim mantemos somente um valor final de pagamento)

		finalizado.setId(Arquivos.Pedidos.ler_pedidos().length);
		Arquivos.Pedidos.inserir_pedidoAntigo(finalizado, this);
		Arquivos.Pedidos.inserir_itensPedido(
            finalizado,
		    finalizado.getItens().toArray(new ItemPedido[0])
		);

		return finalizado; // TODO: retornar um clone
	}

	/** * Cancela e aborta o pedido em andamento no caixa, mantendo o registro histórico
	 * para auditoria e persistindo a transação abortada nos arquivos do sistema.
	 */
	public void cancelarPedidoAtual() {
		if(!possuiPedidoAtual()) {
			throw new Error(
				"não é possível concluir o pedido atual, pois não há um pedido atual associado à loja"
			);
		}

		// Modifica o estado para cancelado mantendo o rastro temporal
		pedidoAtual.setEstado(Pedido.Estado.CANCELADO);
		pedidoAtual.setFinalizadoEm(LocalDateTime.now());

		pedidosAntigos.add(pedidoAtual);
		setPedidoAtual(null);

		// Registra o pedido cancelado para fins de relatório de quebras/cancelamentos
		Arquivos.Pedidos.inserir_pedidoAntigo(pedidoAtual, this);
	}

	// Métodos Getters e Setters de controle operacional de fechamento e caixa

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

	/**
	 * Retorna uma representação resumida do estado dos fechamentos gerados no loop.
	 */
	@Override
	public String toString() {
		String resposta = "";
		for (int i = 0; i < pedidosAntigos.size(); i++) {
			resposta += "Caixa aberto em: " + abertoEm + ", fechado em: " + fechadoEm + ", pagamento total: " + totalPagamento+ ", dinheiro inicial: " + dinheiroInicial + ", dinheiro final: " + dinheiroFinal;
		}
		return resposta;
	}

	/**
	 * Compara de forma estrita duas instâncias de caixas analisando a igualdade profunda de seus atributos.
	 */
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
