package entity;

import dao.UserDAO;

import java.sql.SQLException;

public class Login {
    private UserDAO userDAO;


    public Login() {

    }

    public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean validate(String username, String password) throws SQLException {
        if (userDAO == null)
            userDAO = new UserDAO();
        User user = userDAO.getByUsername(username, password);
        if (user != null) {
            CurrentUser currentUser = CurrentUser.getInstance();
            currentUser.login(user.getId(), user.getName(), "admin".equals(user.getType()));
            return true;
        } else {
            return false;
        }

    }
}
