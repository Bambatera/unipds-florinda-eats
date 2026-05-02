package mx.com.florinda.repositories;

import mx.com.florinda.models.CategoriaCardapio;
import mx.com.florinda.models.ItemCardapio;
import mx.com.florinda.models.ItemCardapioIsento;

import java.sql.*;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class SQLDatabase implements Database {
    @Override
    public CopyOnWriteArrayList<ItemCardapio> listaItensCardapio() {
        CopyOnWriteArrayList<ItemCardapio> itens = new CopyOnWriteArrayList<>();

        String sql = "SELECT id, nome, descricao, categoria, preco, em_promocao, preco_promocional, isento_imposto FROM cardapio.item_cardapio";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "L34ndr0.$1lv@");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                CategoriaCardapio categoria = CategoriaCardapio.valueOf(rs.getString("categoria"));
//                boolean emPromocao = rs.getBoolean("em_promocao");
                double precoPromocional = rs.getDouble("preco_promocional");
                boolean isentoImposto = rs.getBoolean("isento_imposto");

                ItemCardapio itemCardapio = !isentoImposto ? new ItemCardapio(id, nome, descricao, preco, categoria) : new ItemCardapioIsento(id, nome, descricao, preco, categoria);
                itemCardapio.setPromocao(precoPromocional);
                itens.add(itemCardapio);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return itens;
    }

    @Override
    public long totalItensCardapio() {
        long total = 0;

        String sql = "SELECT COUNT(id) FROM cardapio.item_cardapio";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "L34ndr0.$1lv@");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return total;
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio item) {
        String sql = "INSERT INTO cardapio.item_cardapio(nome, descricao, categoria, preco, em_promocao, preco_promocional, isento_imposto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapio", "root", "L34ndr0.$1lv@");
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, item.getNome());
            ps.setString(2, item.getDescricao());
            ps.setString(3, item.getCategoria().name());
            ps.setDouble(4, item.getPreco());
            ps.setBoolean(5, item.isEmPromocao());
            ps.setDouble(6, item.getPrecoComDesconto());
            ps.setBoolean(7, item instanceof ItemCardapioIsento);

            ps.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<ItemCardapio> itemCardapioPorId(Long id) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean removeItemCardapio(Long id) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean alteraPrecoItemCardapio(Long id, double novoPreco) {
        throw new UnsupportedOperationException("TODO");
    }
}
