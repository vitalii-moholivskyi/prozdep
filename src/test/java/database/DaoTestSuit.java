package database;

import dao.DepartmentDAO;
import dao.IDAOGeneric;
import entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import util.DateUtil;

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
    private IDAOGeneric<Scientist> scientistDAO;
    @Autowired
    private IDAOGeneric<Paper> paperDAO;

    @Autowired
    private IDAOGeneric<Topic> topicDAO;

    @Test
    public void testDepartmentDao() {
        Department department = new Department("Super", "+380");
        System.out.println("Before insert:" + department);
        departmentDAO.insert(department);
        System.out.println("Find after insert:");
        System.out.println(departmentDAO.find(department.getId()));

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

    @Test
    public void testScientistDao(){
        Scientist scientist = new Scientist("Super", "+380");

        System.out.println("Before insert:" + scientist);
        scientistDAO.insert(scientist);
        System.out.println("Find after insert:" + scientistDAO.find(scientist.getId()));
        System.out.println("Find All:" + scientistDAO.findAll());

        scientist.setName("New Super");
        scientist.setPhone("New Phone");
        scientistDAO.update(scientist);
        System.out.println("Find after update:" + scientistDAO.find(scientist.getId()));

        scientistDAO.remove(scientist);
        System.out.println("Find after delete:" + scientistDAO.find(scientist.getId()));
    }

    @Test
    public void testPaperDao(){
        Paper paper = new Paper("Ремонт чайників у космосі", "дослідницька робота", 2017);

        System.out.println("Before insert:" + paper);
        paperDAO.insert(paper);
        System.out.println("Find after insert:" + paperDAO.find(paper.getId()));
        System.out.println("Find All:" + paperDAO.findAll());

        paper.setName("NEW Ремонт чайників у космосі\"");
        paper.setType("New дослідницька робота");
        paper.setYear(2018);
        paperDAO.update(paper);
        System.out.println("Find after update:" + paperDAO.find(paper.getId()));

        paperDAO.remove(paper);
        System.out.println("Find after delete:" + paperDAO.find(paper.getId()));
    }

    @Test
    public void testTopicDao(){
        Topic topic = new Topic("Використання BigData для побудови піар компаній",
                "компанія \"Ваш Піар\"",
                DateUtil.getDate("31.09.2009"),
                DateUtil.getDate("31.09.2019"),
                new Department(2),
                new Teacher(2));

        System.out.println("Before insert:" + topic);
        topicDAO.insert(topic);
        System.out.println("Find after insert:" + topicDAO.find(topic.getId()));
        System.out.println("Find All:" + topicDAO.findAll());

        topic.setName("NEW Використання BigData для побудови піар компаній");
        topic.setCliect("New компанія \"Ваш Піар\"");
        topic.setStartDate(DateUtil.getDate("31.09.2010"));
        topic.setEndDate(DateUtil.getDate("31.09.2020"));
        topic.setDepartment(new Department(1));
        topic.setChiefScientist(new Teacher(1));
        topicDAO.update(topic);
        System.out.println("Find after update:" + topicDAO.find(topic.getId()));

        System.out.println("Eager find:" + topicDAO.find(topic.getId(), true));

        topicDAO.remove(topic);
        System.out.println("Find after delete:" + topicDAO.find(topic.getId()));
    }

}
