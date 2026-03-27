package mx.com.florinda.models;

public class ItemCardapio {
    private final long id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final CategoriaCardapio categoria;

    private boolean emPromocao;
    private double precoComDesconto;

    public ItemCardapio(long id, String nome, String descricao, double preco, CategoriaCardapio categoria) {
        if (preco <= 0d) {
            throw new IllegalArgumentException("O preço do item deve ser maior que zero!");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria do item deve ser informada!");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public CategoriaCardapio getCategoria() {
        return categoria;
    }

    public boolean isEmPromocao() {
        return emPromocao;
    }

    public double getPrecoComDesconto() {
        return precoComDesconto;
    }

    public void setPromocao(double precoComDesconto) {
        if (precoComDesconto <= 0d) {
            throw new IllegalArgumentException("O valor do desconto deve ser maior que zero!");
        }
        this.emPromocao = true;
        this.precoComDesconto = precoComDesconto;
    }

    public double getPorcentagemDesconto() {
        return (this.preco - this.precoComDesconto) / (this.preco * 100.0);
    }

    public double getImposto() {
        if (this.emPromocao) {
            return (this.precoComDesconto * 0.1);
        } else {
            return (this.preco * 0.1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.getId()).append("\n");
        sb.append("NOME: ").append(this.getNome()).append("\n");
        sb.append("DESCRIÇÃO: ").append(this.getDescricao()).append("\n");

        if (this.isEmPromocao()) {
            sb.append("ITEM EM PROMOÇÃO").append("\n");
            sb.append("DE: ").append(this.getPreco()).append(" POR: ").append(this.getPrecoComDesconto()).append("\n");
            sb.append((this.getPorcentagemDesconto() * 100)).append("% DE DESCONTO").append("\n");
        } else {
            sb.append("PREÇO: ").append(this.getPreco()).append("\n");
        }

        if (this.getImposto() > 0.00) {
            sb.append("VALOR DO IMPOSTO: ").append(this.getImposto()).append("\n");
        } else {
            sb.append("ITEM ISENTO DE TRIBUTAÇÃO").append("\n");
        }

        sb.append("CATEGORIA: ").append(this.getCategoria()).append("\n");
        return sb.toString();
    }
}
