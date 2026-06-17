package sistema.modelos;

import java.util.Objects;

// Classe representa cada item adicionado ao carrinho.
// Junta o produto lá do catálogo com a quantidade que cliente quer.
public class ItemPedido implements Cloneable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(produto, other.produto) && quantidade == other.quantidade;
	}

	private Produto produto; // Puxa classe Produto
    private int quantidade;

    // Construtor pra instanciar item na hora de rodar gerenciamento
    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Método pra calcular subtotal do produto (preço de Venda * quantidade)
    // Usado no laço de repetição d Pedido: soma conta final
    public double getSubTotal() {
        return (this.produto).getPrecoVenda() * this.quantidade;
    }

    // Getters e setters necessários --> ler dados e salvar nos arquivos
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public ItemPedido clone() {
        ItemPedido i = new ItemPedido(
            this.produto.clone(), this.getQuantidade()
        );

        return i;
    }

    @Override
    public String toString() {
        return String.format(
            "%d x = (%s)",
            getQuantidade(), getProduto().toString()
        );
    }
}
