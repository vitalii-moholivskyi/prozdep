package database;

import dao.IDAOGeneric;
import entity.Department;
import entity.Master;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by mogo on 1/29/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DaoTestSuit {

    @Autowired
    private IDAOGeneric<Department> departmentDAO;
    @Autowired
    private IDAOGeneric<Master> masterDAO;

    @Test
    public void testDepartmentDao() {
        Department department = new Department("Super", "+380");
        System.out.println("Before insert:" + department);
        departmentDAO.insert(department);
        System.out.println("Find after insert:");
        System.out.println(departmentDAO.find(department.getId()));


        testMasterDao(department);

        System.out.println("Find All:");
        System.out.println(departmentDAO.findAll());
        department.setName("New Super");
        System.out.println("Before update:" + department);
        departmentDAO.update(department);
        System.out.println("Find after update:");
        System.out.println(departmentDAO.find(department.getId()));
        System.out.println("DELETE");
        departmentDAO.remove(department);
        System.out.println("Find after remove:");
        System.out.println(departmentDAO.find(department.getId()));
    }


    public void testMasterDao(Department department) {

        Master master = new Master("Full Name","+3-234234", "Topic", new Date(1996,23,12), new Date(1996,23,12), department);
        System.out.println("Before insert:" + master);
        masterDAO.insert(master);
        System.out.println("Find after insert:");
        System.out.println(masterDAO.find(master.getId()));

     /*   System.out.println("Find All:");
        System.out.println(masterDAO.findAll());
        master.setName("New Super");
        System.out.println("Before update:" + master);
        masterDAO.update(master);
        System.out.println("Find after update:");
        System.out.println(masterDAO.find(master.getId()));*/
        System.out.println("DELETE");
        masterDAO.remove(master);
        System.out.println("Find after remove:");
        System.out.println(masterDAO.find(master.getId()));
    }


}
