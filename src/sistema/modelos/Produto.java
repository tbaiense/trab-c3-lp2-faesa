package sistema.modelos;

public class Produto {
	private int idProduto;
	private String nomeProduto;
	private double precoCusto;
	private double precoVenda;

	public Produto() {

	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
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
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
	}

}
