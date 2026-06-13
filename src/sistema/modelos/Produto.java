package sistema.modelos;

public class Produto {
	private int id;
	private String nome;
	private double precoCusto;
	private double precoVenda;

	public Produto() {

	}

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

	public Produto(int idProduto, String nomeProduto, double precoCusto, double precoVenda) {
		super();
		this.id = idProduto;
		this.nome = nomeProduto;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
	}

}
