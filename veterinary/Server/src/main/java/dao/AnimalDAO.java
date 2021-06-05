package dao;

import entity.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO implements DAOApi<Animal> {
    private final Connection connection;
    List<Animal> animals;

    public AnimalDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public Animal getById(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from animals where id = " + id);
        if (rs.next())
            return new Animal(id, rs.getInt("owner"), rs.getString("name"));
        return null;
    }

    @Override
    public List<Animal> getAll() throws SQLException {
        animals = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from animals");
        while (rs.next()) {
            animals.add(new Animal(rs.getInt("id"), rs.getInt("owner"), rs.getString("name")));
        }
        return animals;

    }


    @Override
    public void update(int id, List<String> params) throws SQLException {
        String update =
                "update animals set name = ?, owner = ? where id = ?";
        PreparedStatement updateAnimal = connection.prepareStatement(update);
        updateAnimal.setString(1, params.get(1));
        updateAnimal.setInt(2, Integer.parseInt(params.get(0)));
        updateAnimal.setInt(3, id);
        updateAnimal.executeUpdate();

    }

    @Override
    public void insert(Animal animal) throws SQLException {
        String insert =
                "insert into animals(name, owner) values ( ? , ? )";
        PreparedStatement addAnimal = connection.prepareStatement(insert);
        addAnimal.setString(1, animal.getName());
        addAnimal.setInt(2, animal.getOwnerID());
        addAnimal.executeUpdate();
    }

    @Override
    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "delete from animals where id = " + id);
        } catch (SQLException throwables) {

            Logger logger = Logger.getLogger(AnimalDAO.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }
}
