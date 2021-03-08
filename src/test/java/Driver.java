import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.FinanceAccountDAO;
import com.cardealership.model.FinanceAccount;
import com.cardealership.model.FinancingType;
import com.cardealership.model.OwnedCar;
import com.cardealership.service.AuthMenuService;
import com.cardealership.util.DealershipList;

import java.util.Optional;

public class Driver {
    public static void main(String[] args) throws Exception {
        new AuthMenuService().mainMenu();
//        CarDao dao = DAOUtilities.getCarDao();
//
//        try{
//            Optional<DealershipList<OwnedCar>> cars = dao.getAllByOwnerId(1L);
//            cars.ifPresent(System.out::println);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
