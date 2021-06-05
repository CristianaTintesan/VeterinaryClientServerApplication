package model;

public class Stock {

    private int id;
    private int quantity;
    private String type;

    public Stock(int quantity, String type) {
        this.quantity=quantity;
        this.type=type;
    }

    public Stock(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", type=" + type +
                ", quantity='" + quantity + '\'' +
                '}';
    }

}
