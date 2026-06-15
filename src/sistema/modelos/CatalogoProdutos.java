package sistema.modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sistema.io.Arquivos;

public class CatalogoProdutos {

	private static List<Produto> produtos;

	static {
        produtos = new ArrayList<Produto>();
	}
	// métodos

	/** Cadastra um novo produto no catálogo, salvando no arquivo, e define um novo id.
	*
    * @param p
    * @return
    */
	public static boolean cadastrar(Produto p) {
	    int id = Arquivos.Produtos.ler_produtos().length;
		p.setId(id);

		produtos.add(p);
		Arquivos.Produtos.inserir_produto(p);
		return true;
	}

	public static Produto remover(int id) { // ele via procurar o id do produto pra conseguir fazer a remoção
		for (Produto produto : produtos) {
			if (produto.getId() == id) {
				produtos.remove(produto);

				// TODO: remover produto de arquivo
				// ...

				return produto;
			}
		}

		return null;
	}

	public static Produto buscar(int id) {
		for (Produto produto : produtos) { // o produto do tipo Produto percorre a lista produtos
			if (produto.getId() == id) {
				return produto.clone(); // vai retornar o produto se for true
			}
		}
		return null; // se n encontrar o id ele vai retornar null
	}

	public static Produto atualizar(int id, Produto atualizado) { // função para atualizar os dados usando o id do produto
		for (Produto produto : produtos) {
			if (produto.getId() == id) {
				produto.setNome(atualizado.getNome());
				produto.setPrecoCusto(atualizado.getPrecoCusto());
				produto.setPrecoVenda(atualizado.getPrecoVenda());

				// TODO: atualizar produto no arquivo
				// ...

				return produto;
			}
		}
		return null;
	}

    /** Carrega produtos cadastrados */
	public static void carregarProdutos() {
		CatalogoProdutos.produtos = new ArrayList<Produto>(
    		Arrays.asList(Arquivos.Produtos.ler_produtos())
		);
	}

	public static List<Produto> getProdutos() {
		return produtos;
	}

}
