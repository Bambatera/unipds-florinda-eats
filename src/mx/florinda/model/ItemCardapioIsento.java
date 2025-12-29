package mx.florinda.model;

public class ItemCardapioIsento extends ItemCardapio {

    public ItemCardapioIsento(long id, String nome, String descricao, double preco, CategoriaCardapio categoria) {
        super(id, nome, descricao, preco, categoria);
    }

    @Override
    public double getImposto() {
        return 0;
    }
}
