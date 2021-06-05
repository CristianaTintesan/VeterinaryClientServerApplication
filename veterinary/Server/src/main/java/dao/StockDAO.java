package dao;
import entity.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockDAO implements DAOApi<Stock>{

    private final Connection connection;
    List<Stock> products;

    public StockDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public Stock getById(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from stock where id = " + id);
        if (rs.next())
            return new Stock(id, rs.getInt("quantity"), rs.getString("type"));
        return null;
    }

    @Override
    public List<Stock> getAll() throws SQLException {
        products = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from stock");
        while (rs.next()) {
            products.add(new Stock(rs.getInt("id"), rs.getInt("quantity"), rs.getString("type")));
        }
        return products;

    }


    @Override
    public void update(int id, List<String> params) throws SQLException {
        String update =
                "update stock set type = ?, quantity = ? where id = ?";
        PreparedStatement updateProduct = connection.prepareStatement(update);
        updateProduct.setString(1, params.get(1));
        updateProduct.setInt(2, Integer.parseInt(params.get(0)));
        updateProduct.setInt(3, id);
        updateProduct.executeUpdate();

    }

    @Override
    public void insert(Stock stock) throws SQLException {
        String insert =
                "insert into stock(type, quantity) values ( ? , ? )";
        PreparedStatement addProduct = connection.prepareStatement(insert);
        addProduct.setString(1, stock.getType());
        addProduct.setInt(2, stock.getQuantity());
        addProduct.executeUpdate();
    }

    @Override
    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "delete from stock where id = " + id);
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(StockDAO.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }





}
