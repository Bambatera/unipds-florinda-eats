package mx.com.florinda.repositories;

import mx.com.florinda.controllers.Cardapio;
import mx.com.florinda.models.ItemCardapio;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemCardapioInMemoryRepository implements Database {

    private static final Cardapio cardapio = new Cardapio("/databases/itens-cardapio.json");
//    private static final Cardapio cardapio = new Cardapio("/databases/itens-cardapio.csv");

    private static final CopyOnWriteArrayList<ItemCardapio> itens = cardapio.getItens();

    public ItemCardapioInMemoryRepository() {
    }

    @Override
    public CopyOnWriteArrayList<ItemCardapio> listaItensCardapio() {
        return itens;
    }

    @Override
    public Optional<ItemCardapio> itemCardapioPorId(Long id) {
        return itens.parallelStream().filter(item -> item.getId() == id).findFirst();
    }

    @Override
    public boolean removeItemCardapio(Long id) {
        if (this.itemCardapioPorId(id).isPresent()) {
            this.itemCardapioPorId(id).ifPresent(this.itens::remove);
            return true;
        }
        return false;
    }

    @Override
    public boolean alteraPrecoItemCardapio(Long id, double novoPreco) {
        if (this.itemCardapioPorId(id).isPresent()) {
            this.itemCardapioPorId(id).ifPresent(item -> item.alterarPreco(novoPreco));
            return true;
        }
        return false;
    }

    @Override
    public long totalItensCardapio() {
        return itens.size();
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio item) {
        itens.add(item);
    }
}
