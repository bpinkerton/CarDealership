import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.UserDao;
import com.cardealership.model.AccountType;
import com.cardealership.model.User;
import com.cardealership.service.MenuService;
import com.cardealership.service.UserService;

import java.util.Optional;

public class Driver {
    public static void main(String[] args){
        MenuService menuService = new MenuService();
        menuService.MainMenu();
    }
}
