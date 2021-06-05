package dao;

import entity.Consulation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationDAO implements DAOApi<Consulation> {
    Connection connection;

    public ConsultationDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public Consulation getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Consulation> getAll() throws SQLException {
        List<Consulation> consulations = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from consultations");
        while (rs.next()) {
            consulations.add(new Consulation(rs.getInt("id"), rs.getInt("animalID"), rs.getDate("date") , rs.getString("state")));
        }
        return consulations;
    }

    @Override
    public void update(int id, List<String> params) throws SQLException {
        String update =
                "update consultations set animalID = ?, date = ?, state = ? where id = ?";
        PreparedStatement updateAnimal = connection.prepareStatement(update);
        updateAnimal.setInt(1, Integer.parseInt(params.get(0)));
        updateAnimal.setDate(2, Date.valueOf(params.get(1)));
        updateAnimal.setString(3, params.get(2));
        updateAnimal.setInt(4, id);
        updateAnimal.executeUpdate();
    }

    @Override
    public void insert(Consulation consulation) throws SQLException {
        String insert =
                "insert into consultations(animalID, date, state) values ( ? , ? , ?)";
        PreparedStatement addConsultation = connection.prepareStatement(insert);
        addConsultation.setInt(1, consulation.getAnimalID());
        addConsultation.setDate(2, consulation.getDate());
        addConsultation.setString(3, consulation.getStatus());
        addConsultation.executeUpdate();
    }

    @Override
    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "delete from consultations where id = " + id);
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(ConsultationDAO.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }
}
