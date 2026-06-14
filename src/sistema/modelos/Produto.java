package sistema.modelos;

public class Produto implements Cloneable {
	private int id;
	private String nome;
	private double precoCusto;
	private double precoVenda;

	public Produto() {}

	public int getId() {
		return id;
	}

	public void setId(int idProduto) {
		this.id = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nomeProduto) {
		this.nome = nomeProduto;
	}

	public double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Produto(int id, String nome, double precoCusto, double precoVenda) {
		super();
		this.id = id;
		this.nome = nome;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
	}

	// TODO: FINALIZAR
	@Override
	public String toString() {
	    return String.format(
			"id: %-2d | nome: %s | custo unitário: R$ %.2f | venda: R$ %.2f\n",
		    this.getId(), this.getNome(), this.getPrecoCusto(), this.getPrecoVenda()
		);
	}

	// interface Cloneable
	@Override
	public Produto clone() {
	    Produto p = new Produto();

		p.setId(this.getId());
		p.setNome(this.getNome());
		p.setPrecoCusto(this.getPrecoCusto());
		p.setPrecoVenda(this.getPrecoVenda());

		return p;
	}
}
