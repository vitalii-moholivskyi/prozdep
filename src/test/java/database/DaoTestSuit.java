package database;

import department.dao.*;
import department.entity.*;
import department.model.bo.Paper;
import department.model.bo.Postgraduate;
import department.model.bo.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import util.DateUtil;

/**
 * Created by mogo on 1/29/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DaoTestSuit {

    @Autowired
    private IDepartmentDAO departmentDAO;
    @Autowired
    private IScientistDAO scientistDAO;
    @Autowired
    private IPaperDAO paperDAO;

    @Autowired
    private ITopicDAO topicDAO;

    @Autowired
    private ITeacherDAO teacherDAO;

    @Autowired
    private IMasterDAO masterDAO;

    @Autowired
    private IPostgraduateDAO postgraduateDAO;

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

    @Test
    public void testTeacherDao(){
        Teacher teacher = new Teacher(4,
                "Доцент",
                "Доцент",
                DateUtil.getDate("31.09.2020"), new Department(2));

        System.out.println("Before insert:" + teacher);
        teacherDAO.insert(teacher);
        System.out.println("Find after insert:" + teacherDAO.find(teacher.getId()));
        System.out.println("Find All:" + teacherDAO.findAll());

        teacher.setDegree("New Доцент");
        teacher.setPosition("New Доцент");
        teacher.setStartDate(DateUtil.getDate("31.09.2021"));
        teacher.getDepartment().setId(1);
        teacherDAO.update(teacher);
        System.out.println("Find after update:" + teacherDAO.find(teacher.getId()));

        System.out.println("Eager find:" + teacherDAO.find(teacher.getId(), true));

        teacherDAO.remove(teacher);
        System.out.println("Find after delete:" + teacherDAO.find(teacher.getId()));
    }
    @Test
    public void testPostgraduateDao(){
        Postgraduate postgraduate = new Postgraduate(3,
                "Haskell як релігія",
                DateUtil.getDate("31.09.2000"),
                DateUtil.getDate("31.09.2010"),
                DateUtil.getDate("31.10.2010"),
                new Teacher(2),
                new Department(2));

        System.out.println("Before insert:" + postgraduate);
        postgraduateDAO.insert(postgraduate);
        System.out.println("Find after insert:" + postgraduateDAO.find(postgraduate.getId()));
        System.out.println("Find All:" + postgraduateDAO.findAll());

        postgraduate.setTopic("New Haskell як релігія");
        postgraduate.setStartDate(DateUtil.getDate("31.09.2010"));
        postgraduate.setEndDate(DateUtil.getDate("31.09.2020"));
        postgraduate.setProtectionDate(DateUtil.getDate("31.10.2020"));
        postgraduate.setTeacher(new Teacher(1));
        postgraduate.getDepartment().setId(1);
        postgraduateDAO.update(postgraduate);
        System.out.println("Find after update:" + postgraduateDAO.find(postgraduate.getId()));

        System.out.println("Eager find:" + postgraduateDAO.find(postgraduate.getId(), true));

        postgraduateDAO.remove(postgraduate);
        System.out.println("Find after delete:" + postgraduateDAO.find(postgraduate.getId()));
    }
    @Test
    public void testMasterDao(){
        Master master = new Master(6,
                "Haskell як релігія", DateUtil.getDate("31.09.2000"),
                DateUtil.getDate("31.09.2010"), new Teacher(2), new Department(2));

        System.out.println("Before insert:" + master);
        masterDAO.insert(master);
        System.out.println("Find after insert:" + masterDAO.find(master.getId()));
        System.out.println("Find All:" + masterDAO.findAll());

        master.setTopic("New Haskell як релігія");
        master.setStartDate(DateUtil.getDate("31.09.2010"));
        master.setEndDate(DateUtil.getDate("31.09.2020"));
        master.setTeacher(new Teacher(1));
        master.getDepartment().setId(1);
        masterDAO.update(master);
        System.out.println("Find after update:" + masterDAO.find(master.getId()));

        System.out.println("Eager find:" + masterDAO.find(master.getId(), true));

        masterDAO.remove(master);
        System.out.println("Find after delete:" + masterDAO.find(master.getId()));
    }


}
