import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.util.CarSearchCondition;
import com.cardealership.util.CarSearchQuery;

import java.util.*;

public class Driver {
    public static void main(String[] args) throws Exception {
        CarDao dao = DAOUtilities.getCarDao();
        Map<CarSearchCondition, Object> map = new HashMap<>();
        CarSearchQuery query = new CarSearchQuery();
        query.addCondition(CarSearchCondition.MAKE,"Honda");
        query.addCondition(CarSearchCondition.MODEL,"Civic");


        try{
            Optional result = dao.getAll(query);
            result.ifPresent(System.out::println);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
