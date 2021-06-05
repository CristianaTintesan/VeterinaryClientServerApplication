package entity;

import dao.UserDAO;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Animal {
    private int id;
    private User owner;
    private int ownerID;
    private String name;

    public Animal(User owner, String name) {
        this.owner = owner;
        this.ownerID = owner.getId();
        this.name = name;
    }

    public Animal(int id, int ownerID, String name) {
        UserDAO userDAO = new UserDAO();
        this.id = id;
        this.ownerID = ownerID;
        this.name = name;
        try {
            this.owner = new User(userDAO.getById(ownerID).getId(), userDAO.getById(ownerID).getName(), "Regular user");
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(Animal.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }

    public Animal(String name, int ownerID) {
        this.ownerID = ownerID;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                '}';
    }
}
