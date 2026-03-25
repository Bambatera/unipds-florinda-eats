package mx.com.florinda.model;

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
}
