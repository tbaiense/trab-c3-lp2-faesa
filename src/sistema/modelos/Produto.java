package sistema.modelos;

import java.util.Objects;

public class Produto implements Cloneable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return id == other.id && Objects.equals(nome, other.nome)
				&& Double.doubleToLongBits(precoCusto) == Double.doubleToLongBits(other.precoCusto)
				&& Double.doubleToLongBits(precoVenda) == Double.doubleToLongBits(other.precoVenda);
	}

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
