package model;
import java.sql.SQLException;

public class Login {
    //private UserDAO userDAO;

    public Login() {

    }

    /*public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }*/

    /*public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }*/

    public boolean validate(String username, String password) throws SQLException {
       // if (userDAO == null)
         //   userDAO = new UserDAO();
        //User user = userDAO.getByUsername(username, password);
        if (username != null) {
            CurrentUser currentUser = CurrentUser.getInstance();
            currentUser.login(1, username, "admin".equals(username));
            return true;
        } else {
            return false;
        }

    }

    /*public static boolean validate(User user){

        if (user != null) {
            CurrentUser currentUser = CurrentUser.getInstance();
            currentUser.login(user.getId(), user.getName(), true);
            return true;
        } else {
            return false;
        }

    }*/
}
