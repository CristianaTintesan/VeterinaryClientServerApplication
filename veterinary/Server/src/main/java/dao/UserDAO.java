package dao;

import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements DAOApi<User> {
    Connection connection;

    public UserDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public User getById(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users where id = " + id);
        if (rs.next())
            return new User(id, rs.getString("name"), rs.getInt("type") ==
                    1 ? "Regular user" : "admin");
        return null;
    }

    public User getByUsername(String username, String password) throws SQLException {
        String sqlQuery = "select * from users where name = ?";
        PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
        prepStmt.setString(1, username);
        ResultSet rs = prepStmt.executeQuery();
        User user = null;

        if (rs.next()) {
            if (!rs.getString("password").equals(password))
                return null;
            int type = rs.getInt("type");
            if (type == 1) {
                user = new User(rs.getInt("id"), username, "Regular user");
            } else {
                user = new User(rs.getInt("id"), username, "admin");
            }
        }
        return user;
    }


    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getInt("type") ==
                    1 ? "Regular user" : "Admin"));
        }
        return users;
    }


    @Override
    public void update(int id, List<String> params) throws SQLException {
        String update =
                "update users set name = ? where id = ?";
        PreparedStatement updateAnimal = connection.prepareStatement(update);
        updateAnimal.setString(1, params.get(0));
        updateAnimal.setInt(2, id);
        updateAnimal.executeUpdate();
    }

    @Override
    public void insert(User user) throws SQLException {
        String insert =
                "insert into users(name, password, type) values ( ? , ? , ? )";
        PreparedStatement addUser = connection.prepareStatement(insert);
        addUser.setString(1, user.getName());
        addUser.setString(2, user.getPassword());
        addUser.setInt(3, 1);
        addUser.executeUpdate();
    }

    @Override
    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "delete from users where id = " + id);
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(UserDAO.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }
}
