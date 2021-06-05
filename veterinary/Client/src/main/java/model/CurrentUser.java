package model;

import java.util.Observable;

public class CurrentUser extends Observable {
    private static CurrentUser instance = null;
    private boolean isLoggedIn = false;
    private int id;
    private String name;
    private boolean isAdmin = false;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (instance == null)
            instance = new CurrentUser();
        return instance;
    }


    public void login(int id, String name, boolean isAdmin) {
        isLoggedIn = true;
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
        setChanged();
        notifyObservers();
    }





    public int getId() {
        return id;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
