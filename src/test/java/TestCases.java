import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.UserDao;
import com.cardealership.model.User;
import com.cardealership.service.UserService;
import com.enterprise.annotations.*;

import java.util.Optional;

@TestClass
public class TestCases {
    public TestCases() {
    }

    UserService userService = new UserService();

    @TestMethod(expected = "hondadealer@test.com")
    public String testIfDealershipUserExists(){
        try {
            long id = 0;
            User user = userService.getUserById(id);
            return user.getEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
