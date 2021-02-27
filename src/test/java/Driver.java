import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.model.car.Car;
import com.cardealership.model.car.Ownership;
import com.cardealership.model.user.User;
import com.cardealership.service.AuthMenuService;
import com.cardealership.service.CarService;
import com.cardealership.util.DealershipList;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Driver {
    public static void main(String[] args){
        CarDao cDao = DAOUtilities.getCarDao();
        try{
            Optional<DealershipList<Car>> result = cDao.getAll();
            DealershipList<Car> cars = new DealershipList<>();
            if(result.isPresent())
                cars = result.get();
            System.out.println(cars);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
