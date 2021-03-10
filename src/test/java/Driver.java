import com.cardealership.menu.AuthMenuService;
import com.cardealership.model.User;
import com.cardealership.service.CarService;
import com.cardealership.service.UserService;
import com.enterprise.model.MetaTestData;
import com.enterprise.util.HashMap;
import com.enterprise.util.TestDiscovery;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

public class Driver {
    public static void main(String[] args) throws Exception {
        HashMap<Method, MetaTestData> results = new TestDiscovery().runAndStoreTestInformation();
        System.out.println(results);
    }
}
