package database;

import department.dao.*;
import department.model.bo.*;
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
        Department department = Department.builder()
                .name("Some name")
                .phone("+3 603 320")
                .build();

        System.out.println("Before insert:" + department);
        department = departmentDAO.insert(department);
        System.out.println("Find after insert:");
        System.out.println(departmentDAO.find(department.getId()));

        System.out.println(departmentDAO.findAll());
        department = department.toBuilder().name("New Name").phone("New Phone").build();
        System.out.println("Before update:" + department);
        departmentDAO.update(department);
        System.out.println("Find after update:");
        System.out.println(departmentDAO.find(department.getId()));
        System.out.println("DELETE");
        departmentDAO.remove(department);
        System.out.println("Find after remove:");
        System.out.println(departmentDAO.find(department.getId()));

        System.out.println("Find All:");
        System.out.println("Count:" + departmentDAO.count());
        System.out.println("Find all with limit, offset:" + departmentDAO.findAll(2,1));
    }

    @Test
    public void testScientistDao(){
        Scientist scientist = Scientist.builder()
                .name("Super")
                .phone("+380")
                .build();

        System.out.println("Before insert:" + scientist);
        scientist = scientistDAO.insert(scientist);
        System.out.println("Find after insert:" + scientistDAO.find(scientist.getId()));


        scientist = scientist.toBuilder()
                .name("New Super")
                .phone("New Phone")
                .build();

        scientistDAO.update(scientist);
        System.out.println("Find after update:" + scientistDAO.find(scientist.getId()));

        scientistDAO.remove(scientist);
        System.out.println("Find after delete:" + scientistDAO.find(scientist.getId()));

        System.out.println("Find All:" + scientistDAO.findAll());
        System.out.println("Count:" + scientistDAO.count());
        System.out.println("Find all with limit, offset:" + scientistDAO.findAll(5,10));
    }

    @Test
    public void testPaperDao(){
        Paper paper = Paper.builder()
                .name("Ремонт чайників у космосі")
                .type("дослідницька робота")
                .year(2017)
                .build();

        System.out.println("Before insert:" + paper);
        paper = paperDAO.insert(paper);
        System.out.println("Find after insert:" + paperDAO.find(paper.getId()));

        paper = paper.toBuilder()
                .name("NEW Ремонт чайників у космосі\"")
                .type("New дослідницька робота")
                .year(2018)
                .build();

        paperDAO.update(paper);
        System.out.println("Find after update:" + paperDAO.find(paper.getId()));

        paperDAO.remove(paper);
        System.out.println("Find after delete:" + paperDAO.find(paper.getId()));

        System.out.println("Find All:" + paperDAO.findAll());
        System.out.println("Count:" + paperDAO.count());
        System.out.println("Find all with limit, offset:" + paperDAO.findAll(5,10));
    }

    @Test
    public void testTopicDao(){
        Topic topic = Topic.builder()
                .name("Використання BigData для побудови піар компаній")
                .client("компанія \"Ваш Піар\"")
                .startDate(DateUtil.getDate("31.09.2009"))
                .endDate(DateUtil.getDate("31.09.2019"))
                .department(Department.builder().id(2).build())
                .chiefScientist(Teacher.builder().id(2).build())
                .build();

        System.out.println("Before insert:" + topic);
        topic = topicDAO.insert(topic);
        System.out.println("Find after insert:" + topicDAO.find(topic.getId()));

        topic = topic.toBuilder()
                .name("NEW Використання BigData для побудови піар компаній")
                .client("New компанія \"Ваш Піар\"")
                .startDate(DateUtil.getDate("31.09.2010"))
                .endDate(DateUtil.getDate("31.09.2020"))
                .department(Department.builder().id(1).build())
                .chiefScientist(Teacher.builder().id(1).build())
                .build();

        topicDAO.update(topic);
        System.out.println("Find after update:" + topicDAO.find(topic.getId()));

        System.out.println("Eager find:" + topicDAO.find(topic.getId(), true));

        topicDAO.remove(topic);
        System.out.println("Find after delete:" + topicDAO.find(topic.getId()));

        System.out.println("Find All:" + topicDAO.findAll());
        System.out.println("Count:" + topicDAO.count());
        System.out.println("Find all with limit, offset:" + topicDAO.findAll(5,10));
    }

    @Test
    public void testTeacherDao(){
        Teacher teacher = Teacher.builder()
                .name("Super")
                .phone("+380")
                .position("Доцент")
                .degree("Доцент")
                .startDate(DateUtil.getDate("31.09.2020"))
                .department(Department.builder().id(2).build())
                .build();

        System.out.println("Before insert:" + teacher);
        teacher = teacherDAO.insert(teacher);
        System.out.println("Find after insert:" + teacherDAO.find(teacher.getId()));

        teacher = teacher.toBuilder()
                .name("NEW Super")
                .phone("+NEW 380")
                .degree("New Доцент")
                .position("New Доцент")
                .startDate(DateUtil.getDate("31.09.2021"))
                .department(Department.builder().id(1).build())
                .build();

        teacherDAO.update(teacher);
        System.out.println("Find after update:" + teacherDAO.find(teacher.getId()));

        System.out.println("Eager find:" + teacherDAO.find(teacher.getId(), true));

        teacherDAO.remove(teacher);
        System.out.println("Find after delete:" + teacherDAO.find(teacher.getId()));

        System.out.println("Find All:" + teacherDAO.findAll());
        System.out.println("Count:" + teacherDAO.count());
        System.out.println("Find all with limit, offset:" + teacherDAO.findAll(1,1));
    }


    @Test
    public void testPostgraduateDao(){
        Postgraduate postgraduate = Postgraduate.builder()
                .name("Super")
                .phone("+380")
                .topic( "Haskell як релігія")
                .startDate(DateUtil.getDate("31.09.2000"))
                .endDate(DateUtil.getDate("31.09.2010"))
                .protectionDate(DateUtil.getDate("31.10.2010"))
                .teacher(Teacher.builder().id(2).build())
                .department(Department.builder().id(2).build())
                .build();

        System.out.println("Before insert:" + postgraduate);
        postgraduate = postgraduateDAO.insert(postgraduate);
        System.out.println("Find after insert:" + postgraduateDAO.find(postgraduate.getId()));

        postgraduate = postgraduate.toBuilder()
                .name("NEW Super")
                .phone("+NEW 380")
                .topic("New Haskell як релігія")
                .startDate(DateUtil.getDate("31.09.2010"))
                .endDate(DateUtil.getDate("31.09.2020"))
                .protectionDate(DateUtil.getDate("31.10.2020"))
                .teacher(Teacher.builder().id(1).build())
                .department(Department.builder().id(1).build())
                .build();
        postgraduateDAO.update(postgraduate);
        System.out.println("Find after update:" + postgraduateDAO.find(postgraduate.getId()));

        System.out.println("Eager find:" + postgraduateDAO.find(postgraduate.getId(), true));

        postgraduateDAO.remove(postgraduate);
        System.out.println("Find after delete:" + postgraduateDAO.find(postgraduate.getId()));

        System.out.println("Find All:" + postgraduateDAO.findAll());
        System.out.println("Count:" + postgraduateDAO.count());
        System.out.println("Find all with limit, offset:" + postgraduateDAO.findAll(1,2));
    }
    @Test
    public void testMasterDao(){
        Master master = Master.builder()
                .name("Super")
                .phone("+380")
                .topic( "Haskell як релігія")
                .startDate(DateUtil.getDate("31.09.2000"))
                .endDate(DateUtil.getDate("31.09.2010"))
                .teacher(Teacher.builder().id(2).build())
                .department(Department.builder().id(2).build())
                .build();

        System.out.println("Before insert:" + master);
        master = masterDAO.insert(master);
        System.out.println("Find after insert:" + masterDAO.find(master.getId()));

        master = master.toBuilder()
                .name("NEW Super")
                .phone("+NEW 380")
                .topic("New Haskell як релігія")
                .startDate(DateUtil.getDate("31.09.2010"))
                .endDate(DateUtil.getDate("31.09.2020"))
                .teacher(Teacher.builder().id(1).build())
                .department(Department.builder().id(1).build())
                .build();

        masterDAO.update(master);
        System.out.println("Find after update:" + masterDAO.find(master.getId()));

        System.out.println("Eager find:" + masterDAO.find(master.getId(), true));

        masterDAO.remove(master);
        System.out.println("Find after delete:" + masterDAO.find(master.getId()));

        System.out.println("Find All:" + masterDAO.findAll());
        System.out.println("Count:" + masterDAO.count());
        System.out.println("Find all with limit, offset:" + masterDAO.findAll(2,2));
    }


    @Test
    public void testSearchDaoMethods(){
        System.out.println(teacherDAO.findAll("Сергій"));
        System.out.println(teacherDAO.count("Сергій"));
        System.out.println(teacherDAO.findAll("Сергій", 2,10));
    }

}
