package sistema.modelos;

import java.util.ArrayList;
import java.util.List;

public class CatalogoProdutos {

	private static List<Produto> produtos;


	public static boolean cadastar(Produto p) {
		for (Produto produto : produtos) {
			if (produto.getIdProduto() == p.getIdProduto()) {
				return false;
			}
		}
		produtos.add(p);
		return true;
	}

	public static Produto remover(int id) { // ele via procurar o id do produto pra conseguir fazer a remoção
		for (Produto produto : produtos) {
			if (produto.getIdProduto() == id) {
				produtos.remove(produto);
				return produto;
			}
		}
		return null;
	}

	public static Produto buscar(int id) {
		for (Produto produto : produtos) { // o produto do tipo Produto percorre a lista produtos
			if (produto.getIdProduto() == id) {
				return produto; // vai retornar o produto se for true
			}
		}
		return null; // se n encontrar o id ele vai retornar null
	}

	public static Produto atualizar(int id, Produto atualizado) { // função para atualizar os dados usando o id do produto
		for (Produto produto : produtos) {
			if (produto.getIdProduto() == id) {
				produto.setNomeProduto(atualizado.getNomeProduto());
				produto.setPrecoCusto(atualizado.getPrecoCusto());
				produto.setPrecoVenda(atualizado.getPrecoVenda());
				return produto;
			}
		}
		return null;
	}
}
