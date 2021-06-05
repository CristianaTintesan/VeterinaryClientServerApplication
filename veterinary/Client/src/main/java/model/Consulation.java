package model;

import java.sql.Date;


public class Consulation {
    private int id;
    private Animal animal;
    private String animalName;
    private int animalID;
    private int ownerID;
    private String ownerName;
    private Date date;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




    /*public Consulation(int id, int animalID, Date date) {
        AnimalDAO animalDAO = new AnimalDAO();
        this.id = id;
        this.animalID = animalID;
        this.date = date;
        try {
            animal = animalDAO.getById(animalID);
            animalName = animal.getName();
            ownerID = animal.getOwnerID();
            ownerName = animal.getOwner().getName();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/



    public Consulation(){

    }


    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnername() {
        return ownerName;
    }

    public void setOwnername(String ownername) {
        this.ownerName = ownername;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Consulation{" +
                "id=" + id +
                ", animal=" + animal +
                ", date=" + date +
                '}';
    }
}
