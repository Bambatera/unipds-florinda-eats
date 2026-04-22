package mx.com.florinda.repositories;

import mx.com.florinda.models.ItemCardapio;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Database {

    CopyOnWriteArrayList<ItemCardapio> listaItensCardapio();

    Optional<ItemCardapio> itemCardapioPorId(Long id);

    boolean removeItemCardapio(Long id);

    boolean alteraPrecoItemCardapio(Long id, double novoPreco);

    int totalItensCardapio();

    void adicionaItemCardapio(ItemCardapio item);

}
