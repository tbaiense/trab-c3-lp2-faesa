package sistema.modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sistema.io.Arquivos;

public class CatalogoProdutos {

	@Override
	public String toString() {
		return "[modelos: CatalogoProdutos]";
	}

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
		int idMax = -1;

		// gera um id temporário e verifica se já foi usado
		for (var prodAnalizar: produtos) {
		   if (prodAnalizar.getId() > idMax) {
				idMax = prodAnalizar.getId();
			}
		}
		p.setId(idMax + 1);

		produtos.add(p);
		Arquivos.Produtos.inserir_produto(p);
		return true;
	}

	public static Produto remover(int id) { // ele via procurar o id do produto pra conseguir fazer a remoção
		for (Produto produto : produtos) {
			if (produto.getId() == id) {
				produtos.remove(produto);
				Arquivos.Produtos.remover_produto(id);

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

				Arquivos.Produtos.atualizar_produto(id, atualizado);
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
