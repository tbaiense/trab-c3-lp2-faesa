package sistema.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pedido {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return estado == other.estado && Objects.equals(finalizadoEm, other.finalizadoEm)
				&& Objects.equals(formaPagamento, other.formaPagamento) && id == other.id
				&& Objects.equals(itens, other.itens)
				&& Double.doubleToLongBits(precoVendaTotal) == Double.doubleToLongBits(other.precoVendaTotal)
				&& Double.doubleToLongBits(taxaCartao) == Double.doubleToLongBits(other.taxaCartao)
				&& Double.doubleToLongBits(trocoCalculado) == Double.doubleToLongBits(other.trocoCalculado)
				&& Double.doubleToLongBits(valorEntradaCliente) == Double.doubleToLongBits(other.valorEntradaCliente)
				&& Objects.equals(vendedor, other.vendedor);
	}

	public static enum Estado {
        ABERTO,
        CONCLUIDO,
        CANCELADO
    }

    /** Caso forma de pagamento seja CARTAO, taxa aplicada sobre sobre o subTotal dos itens para gerar precoVendaTotal */
    public static final double TAXA_CARTAO_PADRAO = 0.15;

    private int id;
    private List<ItemPedido> itens; // representa carrinho de compras
    private Funcionario vendedor; // pra alinhar com projeto
    private double precoVendaTotal; // valor exibido ao cliente (o mesmo independente da forma de pagamento)

    /** Valor em dinheiro dado pelo cliente, caso forma de pagamento DINHEIRO */
    private double valorEntradaCliente;
    private double trocoCalculado; // dinheiro dado ao cliente, caso forma de pagamento seja "DINHEIRO"

    private double taxaCartao;
    private LocalDateTime finalizadoEm;
    private String formaPagamento; // CARTAO || PIX || DINHEIRO
    private Estado estado;

    // construtor alinhado com classe Caixa
    // pedido vazio pro sistema não quebrar quando juntar tudo
    public Pedido() {
        this.itens = new ArrayList<>(); // pra não deixar lista começar em null
        this.precoVendaTotal = 0.0;
        this.taxaCartao = 0.0;
        this.estado = Estado.ABERTO;
        this.formaPagamento = null;
        this.finalizadoEm = null;
    }

    public int getId() {
       return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItens(ItemPedido[] itens) {
        this.itens = Arrays.asList(itens);
        recalcularPrecoTotal();
    }

    /** Insere um novo produto na lista de itens ou atualiza a quantidade, caso ele já exista.
    *
    * @param produto {@link Produto} a ser adicionado ou atualizado.
    * @param quantidade
 */
    public void atualizarItem(Produto produto, int quantidade) {
        if (isFinalizado()) { // validade se pedido foi finalizado
            System.out.println("Erro: Não é possível alterar um pedido já finalizado.");
            return;
        }

        if (produto == null) {
        	throw new IllegalArgumentException("Produto não pode ser nulo para atualização");
        }

        ItemPedido itemExistente = buscarItem(produto.getId());

        if (itemExistente != null) { // se não nula, quer dizer que esse item já existia no carrinho
            if (quantidade <= 0) { // quantidade alterada pra zero == item deve ser removido
                itens.remove(itemExistente);
            } else {
                itemExistente.setQuantidade(quantidade); // quantidade alterada e ainda positiva == substitui o número de quantidade antiga pelo novo
            }
        } else if (quantidade > 0) { // se quantidade ainda ficar nula == produto adicionado ao carrinho pela primeira vez
        	// cria objeto novo e adiciona na lista que representa o carrinho
            itens.add(new ItemPedido(produto, quantidade));
        }

        recalcularPrecoTotal(); // atualiza o valor conforme cada caso
    }

    // tirar um produto do pedido
    public void removerItem(int idProduto) {
        if (isFinalizado()) {
            System.out.println("Erro: Não é possível alterar um pedido já finalizado.");
            return;
        }

        itens.removeIf(item -> item.getProduto().getId() == idProduto);//removeIf --> varre lista e remove o que tiver ID igual à "idProduto"
        recalcularPrecoTotal(); // atualiza valor depois da remoção
    }

    /** Busca por um item usando o id do produto.
    *
    * @param idProduto id do {@link Produto} a ser buscado na lista de itens.
    * @return um clone do {@link ItemPedido}
    */
    public ItemPedido buscarItem(int idProduto) {
        for (var i: getItens()) {
           if (i.getProduto().getId() == idProduto) {
               return i.clone();
           }
        }

        return null;
    }

    // método privado --> atualizar total do carrinho
    private void recalcularPrecoTotal() {
        this.precoVendaTotal = 0.0; // zera antes de somar, pra não acumular alores antigos quando chamado
        for (ItemPedido item : itens) {
            this.precoVendaTotal += item.getSubTotal(); // preço X quantidade dos itens no carrinho de antes de somar --> evitar acumular coisas na variável
        }
    }

    // getters e setters --> salvar arquivos
    public List<ItemPedido> getItens() { return itens; }

    public Funcionario getVendedor() { return vendedor; }

    public void setValorEntradaCliente(double valorEntradaCliente) {
        this.valorEntradaCliente = valorEntradaCliente;
    }

    public double getValorEntradaCliente() {
        return this.valorEntradaCliente;
    }

    public void setTrocoCalculado(double trocoCalculado) {
        this.trocoCalculado = trocoCalculado;
    }

    public double getTrocoCalculado() {
        return this.trocoCalculado;
    }


    public void setVendedor(Funcionario vendedor) { this.vendedor = vendedor; }

    public double getPrecoVendaTotal() { return precoVendaTotal; }

    public double getLucro() {
        List<ItemPedido> itens = getItens();
        double custoTotal = 0.0;

        for (var i: itens) {
            custoTotal += i.getProduto().getPrecoCusto() * i.getQuantidade();
        }

        double venda = getPrecoVendaTotal();
        double desconto = getPrecoVendaTotal() * taxaCartao;
        return venda - desconto - custoTotal;

    }
    public double getTaxaCartao() { return taxaCartao; }

    public void setFinalizadoEm(LocalDateTime finalizadoEm) {
        this.finalizadoEm = finalizadoEm;
    }

    public LocalDateTime getFinalizadoEm() { return finalizadoEm; }

    /** Define a forma de pagamento
    *
    * @param formaPagamento null ou String que deve ser {@code CARTAO}, {@code PIX} ou {@code DINHEIRO}
    */
    private void setFormaPagamento(String formaPagamento) {
        if (formaPagamento == null) {
            this.formaPagamento = null;
            return;
        }

        String formaUpper = formaPagamento.toUpperCase();

        if (!formaUpper.equals("CARTAO")
            && !formaUpper.equals("PIX")
            && !formaUpper.equals("DINHEIRO")
        ) {
            throw new IllegalArgumentException(
                "Valor de forma de pagamento inválido. Deve ser `CARTAO`, `PIX` ou `DINHEIRO`"
            );
        }

        this.taxaCartao = formaUpper.equals("CARTAO")
            ? TAXA_CARTAO_PADRAO
            : 0.0;


        this.formaPagamento = formaUpper;
    }
    public void setTaxaCartao(double taxaCartao) {
        this.taxaCartao = taxaCartao;
    }

    public String getFormaPagamento() { return formaPagamento; }

    /** Retorna qual seria o valor de troco dado o valor de entrada. NÃO altera a variável interna de trocoEmitido
    *
    * @param valorEntrada valor pago pelo cliente. Deve ser maior ou igual ao precoCustoTotal.
    */
    public double calcularTroco(double valorEntrada) {
        if (valorEntrada < 0) {
            throw new IllegalArgumentException("Valor de entrada deve ser maior ou igual a zero");
        }

        double troco = valorEntrada - precoVendaTotal;

        if (troco < 0) {
            throw new IllegalArgumentException(
                "Valor de entrada para pagamento deve ser maior ou igual ao total do pedido"
            );
        }

        return troco;
    }

    /** Recebe o pagamento e calcula os valores internos do pedido.
    * Precondição: precoTotalVenda já deve estar definido.
    *
    * @param formaPagamento
    * @param valorEntrada
    * @return o valor do troco, se for necessário
 */
    public double receberPagamento(String formaPagamento, double valorEntrada) {
        if (itens.isEmpty()) {
            throw new IllegalStateException(
                "É necessário adicionar ao menos um item antes de receber o pagamento do pedido"
            );
        }

        double troco = calcularTroco(valorEntrada);

        try {
            setFormaPagamento(formaPagamento);
        } catch (Exception ex) {
            setFormaPagamento(null);
            throw ex;
        }

       if (formaPagamento.equals("DINHEIRO")) {
           this.valorEntradaCliente = valorEntrada;
       } else {
           setValorEntradaCliente(this.getPrecoVendaTotal());
       }

       this.trocoCalculado = troco;

       return troco;
    }

    public boolean isFinalizado() {
        return estado == Estado.CONCLUIDO || estado == Estado.CANCELADO;
    }

    public void setEstado(Estado e) {
        this.estado = e;
    }

    public Estado getEstado() {
        return this.estado;
    }

    @Override
    public String toString() {
        return String.format(
            "id: %d | precoVendaTotal: R$ %.2f | Lucro: %.2f | Qtd itens: %d | Estado: %s | Finalizado em: %s",
            this.getId(),
            this.getPrecoVendaTotal(),
            this.getLucro(),
            this.itens.size(),
            this.estado.toString(),
            (
                this.finalizadoEm != null
                ? this.finalizadoEm.toString()
                : "NUNCA"
            )
        );
    }
}
