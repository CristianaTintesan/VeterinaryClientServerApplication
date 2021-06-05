import entity.CurrentUser;
import entity.Login;
import entity.User;
import dao.UserDAO;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Test;

import java.sql.SQLException;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

public class TestLogin extends TestCase {

    private User existingUser;
    private User nonExistingUser;
    private User adminUser;
    private User regularUser;
    private UserDAO userDAO;

    public void setup() {
        userDAO = EasyMock.createMock(UserDAO.class);
        existingUser = new User(1, "admin", "admin");
        nonExistingUser = null;


        adminUser = new User(1, "admin", "admin");
        regularUser = new User(1, "regular", "regular");

    }

    @Test
    public void testValidLogin() throws SQLException {
        setup();
        expect(userDAO.getByUsername("admin", "admin")).andReturn(existingUser);
        replay(userDAO);
        Login login = new Login(userDAO);

        boolean checkStatus = login.validate("admin", "admin");
        assertTrue(checkStatus);


    }

    @Test
    public void testInvalidLogin() throws SQLException {
        setup();
        expect(userDAO.getByUsername("admin5", "admin")).andReturn(nonExistingUser);
        replay(userDAO);
        Login login = new Login(userDAO);

        boolean checkStatus = login.validate("admin5", "admin");
        assertFalse(checkStatus);
    }

    @Test
    public void testAdminUser() throws SQLException {
        setup();
        expect(userDAO.getByUsername("admin", "admin")).andReturn(adminUser);
        replay(userDAO);
        Login login = new Login(userDAO);

        login.validate("admin", "admin");
        boolean checkStatus = CurrentUser.getInstance().isAdmin();
        assertTrue(checkStatus);
    }

    @Test
    public void testRegularUser() throws SQLException {
        setup();
        expect(userDAO.getByUsername("regular", "regular")).andReturn(regularUser);
        replay(userDAO);
        Login login = new Login(userDAO);

        login.validate("regular", "regular");
        boolean checkStatus = CurrentUser.getInstance().isAdmin();
        assertFalse(checkStatus);
    }


}
