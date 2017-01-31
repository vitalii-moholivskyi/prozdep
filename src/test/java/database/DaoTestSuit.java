package database;

import department.dao.DepartmentDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mogo on 1/29/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DaoTestSuit {

    @Autowired
    private DepartmentDAO departmentDAO;
    @Test
    public void testOrderService() {
        System.out.println(departmentDAO.findAll());
        System.out.println("Something is working");
    }
}
